package graduationProject.IMUISystem.eventHandler.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import graduationProject.IMUISystem.eventHandler.dto.MenuDto;
import graduationProject.IMUISystem.eventHandler.entity.*;
import graduationProject.IMUISystem.eventHandler.controller.exception.EventAlreadyExistException;
import graduationProject.IMUISystem.eventHandler.controller.exception.EventNotExistException;
import graduationProject.IMUISystem.eventHandler.controller.exception.JsonMappingException;
import graduationProject.IMUISystem.eventHandler.dto.CustomizeEventDto;
import graduationProject.IMUISystem.eventHandler.dto.UserStateDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class RepositoryServiceImpl implements RepositoryService{
    private final CustomizeEventRepository customizeEventRepository;
    private final UserStateRepository userStateRepository;
    private final ObjectMapper objectMapper;
    private final MenuRepository menuRepository;

    @PostConstruct
    public void init() {
        String temp;
        //Login event
        {
            try {
                temp = objectMapper.writeValueAsString(
                        new ArrayList<>() {{
                            add(
                                    CustomizeEventVariable.builder()
                                            .variableName("USERNAME")
                                            .displayNameTemplate("username")
                                            .build()
                            );
                            add(
                                    CustomizeEventVariable.builder()
                                            .variableName("PASSWORD")
                                            .displayNameTemplate("password")
                                            .build()
                            );
                        }}
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            customizeEventRepository.save(
                    CustomizeEvent.builder()
                            .eventName("LOGIN")
                            .descriptionTemplate("Please enter your %s!")
                            .variables(temp)
                            .build()
            );
        }
        //Sign up event
        {
            try {
                temp = objectMapper.writeValueAsString(
                        new ArrayList<>() {{
                            add(
                                    CustomizeEventVariable.builder()
                                            .variableName("USER_DISPLAY_NAME")
                                            .displayNameTemplate("display name")
                                            .build()
                            );
                            add(
                                    CustomizeEventVariable.builder()
                                            .variableName("USERNAME")
                                            .displayNameTemplate("username")
                                            .build()
                            );
                            add(
                                    CustomizeEventVariable.builder()
                                            .variableName("PASSWORD")
                                            .displayNameTemplate("password")
                                            .build()
                            );
                        }}
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            customizeEventRepository.save(
                    CustomizeEvent.builder()
                            .eventName("SIGN_UP")
                            .descriptionTemplate("Please enter your %s!")
                            .variables(temp)
                            .build()
            );
        }
        //Login or Sign up menu
        {
            try {
                temp = objectMapper.writeValueAsString(
                        new ArrayList<>(){{
                            add(
                                    MenuOption.builder()
                                            .displayName("Login")
                                            .nextEvent("LOGIN")
                                            .build()
                            );
                            add(
                                    MenuOption.builder()
                                            .displayName("Sign up")
                                            .nextEvent("SIGN_UP")
                                            .build()
                            );
                        }}
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            menuRepository.save(
                    Menu.builder()
                            .menuName("LOGIN_OR_SIGN_UP_MENU")
                            .description("Login or Sign up!\n")
                            .options(temp)
                            .parameters("{}")
                            .build()
            );
        }
        //Default menu
        {
            if(!menuRepository.existsById("DEFAULT_MENU"))
                menuRepository.save(
                        Menu.builder()
                                .menuName("DEFAULT_MENU")
                                .description("Hello!\nWhat are you looking for?\n")
                                .options("[]")
                                .parameters("{}")
                                .build()
                );
        }
    }
    @Override
    public boolean isUserHaveState(IMUserData imUserData) {
        return userStateRepository.existsById(imUserData);
    }

    @Override
    public void newUserStateDto(UserStateDto userStateDto) {
        String data;
        String parameters;
        try {
            data = objectMapper.writeValueAsString(userStateDto.getData());
        } catch (JsonProcessingException e) {
            throw new JsonMappingException(userStateDto.getData().toString(),"String");
        }
        try{
            parameters = objectMapper.writeValueAsString(userStateDto.getParameters());
        }catch (JsonProcessingException e) {
            throw new JsonMappingException(userStateDto.getParameters().toString(),"String");
        }
        userStateRepository.save(
                UserState.builder()
                        .imUserData(userStateDto.getImUserData())
                        .username(userStateDto.getUsername())
                        .eventName(userStateDto.getEventName())
                        .description(userStateDto.getDescription())
                        .data(data)
                        .parameters(parameters)
                        .build()
        );
    }

    @Override
    public void removeUserState(IMUserData imUserData) {
        userStateRepository.deleteById(imUserData);
    }


    public UserState getUserState(IMUserData imUserData) {
        if(!userStateRepository.existsById(imUserData)) throw new RuntimeException(String.format("User %s not exist",imUserData));
        return userStateRepository.getReferenceById(imUserData);
    }

    @Override
    public UserStateDto getUserStateDto(IMUserData imUserData) {
        UserState userState = getUserState(imUserData);
        UserStateDto userStateDto = UserStateDto.builder()
                .username(userState.getUsername())
                .description(userState.getDescription())
                .imUserData(userState.getImUserData())
                .eventName(userState.getEventName())
                .build();
        Map<String,Object> parameters;
        List<?> data;
        try {
            parameters = objectMapper.readValue(userState.getParameters(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new JsonMappingException(userState.getParameters(),"Map<String,Object>");
        }
        try {
            if(userState.getEventName().equals("MENU"))
                data = objectMapper.readValue(userState.getData(), new TypeReference<List<MenuOption>>() {});
            else
                data = objectMapper.readValue(userState.getData(), new TypeReference<List<CustomizeEventVariable>>() {});
        } catch (JsonProcessingException e) {
            throw new JsonMappingException(userState.getData(),"List<?>");
        }

        userStateDto.setData(data);
        userStateDto.setParameters(parameters);
        return userStateDto;
    }

    @Override
    public boolean checkEventName(String eventName) {
        return customizeEventRepository.existsById(eventName);
    }

    @Override
    public List<String> getAllEvent() {
        return customizeEventRepository.getAllEventName();
    }

    @Override
    public CustomizeEventDto getEventDto(String eventName) {
        if(!customizeEventRepository.existsById(eventName)) throw new EventNotExistException(eventName);
        CustomizeEvent customizeEvent = customizeEventRepository.getReferenceById(eventName);
        CustomizeEventDto customizeEventDto =
                CustomizeEventDto.builder()
                        .eventName(customizeEvent.getEventName())
                        .descriptionTemplate(customizeEvent.getDescriptionTemplate())
                        .build();
        List<CustomizeEventVariable> variables;
        try {
            variables = objectMapper.readValue(customizeEvent.getVariables(),new TypeReference<>(){});
        } catch (JsonProcessingException e) {
            throw new JsonMappingException(customizeEvent.getVariables(),"List<CustomizeEventVariable>");
        }
        customizeEventDto.setVariables(variables);
        return customizeEventDto;
    }

    @Override
    public void newEvent(CustomizeEventDto customizeEventDto) {
        if(customizeEventRepository.existsById(customizeEventDto.getEventName())) throw new EventAlreadyExistException(customizeEventDto.getEventName());
        String variables;
        try {
            variables = objectMapper.writeValueAsString(customizeEventDto.getVariables());
        } catch (JsonProcessingException e) {
            throw new JsonMappingException(customizeEventDto.getVariables().toString(),"String");
        }
        customizeEventRepository.save(
                CustomizeEvent.builder()
                        .eventName(customizeEventDto.getEventName())
                        .descriptionTemplate(customizeEventDto.getDescriptionTemplate()==null?"%s":customizeEventDto.getDescriptionTemplate())
                        .variables(variables)
                        .build()
        );
    }


    @Override
    public void deleteEvent(String eventName) {
        customizeEventRepository.deleteById(eventName);
    }

    @Override
    public MenuDto getMenuDto(String menuName) {
        if(!menuRepository.existsById(menuName)) throw new RuntimeException("menu not exist!");
        Menu menu = menuRepository.getReferenceById(menuName);
        List<MenuOption> options;
        Map<String,Object> parameters;
        try {
            options = objectMapper.readValue(menu.getOptions(), new TypeReference<>() {});
            parameters = objectMapper.readValue(menu.getParameters(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return MenuDto.builder()
                .menuName(menuName)
                .description(menu.getDescription())
                .options(options)
                .parameters(parameters)
                .build();
    }

    @Override
    public void setMenuDto(MenuDto menuDto) {
        String options;
        String parameters;
        try {
            options = objectMapper.writeValueAsString(menuDto.getOptions());
            parameters = objectMapper.writeValueAsString(menuDto.getParameters());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        menuRepository.save(
                Menu.builder()
                        .menuName(menuDto.getMenuName())
                        .description(menuDto.getDescription())
                        .options(options)
                        .parameters(parameters)
                        .build()
        );
    }

    @Override
    public void addOptionToDefaultMenu(MenuOption menuOption) {
        if(!menuRepository.existsById("DEFAULT_MENU")) throw new RuntimeException("DEFAULT_MENU not exist!");
        if(!customizeEventRepository.existsById(menuOption.getNextEvent())) throw new EventNotExistException(menuOption.getNextEvent());
        MenuDto menuDto = getMenuDto("DEFAULT_MENU");
        menuDto.getOptions().add(menuOption);
        setMenuDto(menuDto);
    }

    @Override
    public void removeOptionFromDefaultMenu(MenuOption menuOption) {
        if(!menuRepository.existsById("DEFAULT_MENU")) throw new RuntimeException("DEFAULT_MENU not exist!");
        MenuDto menuDto = getMenuDto("DEFAULT_MENU");
        menuDto.getOptions().remove(menuOption);
        setMenuDto(menuDto);
    }




}
