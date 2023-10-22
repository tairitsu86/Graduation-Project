package graduationProject.IMUISystem.eventHandler.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    @PostConstruct
    public void init() {
        String temp;
        try {
            temp = objectMapper.writeValueAsString(
                    new ArrayList<>(){{
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
                        .description("Please enter your %s!")
                        .variables(temp)
                        .build()
        );
        try {
            temp = objectMapper.writeValueAsString(
                    new ArrayList<>(){{
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
                        .description("Please enter your %s!")
                        .variables(temp)
                        .build()
        );
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
        Map<String,String> parameters;
        List<?> data;
        try {
            parameters = objectMapper.readValue(userState.getParameters(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new JsonMappingException(userState.getParameters(),"Map<String,String>");
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
                        .description(customizeEvent.getDescription())
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
                        .description(customizeEventDto.getDescription()==null?"%s":customizeEventDto.getDescription())
                        .variables(variables)
                        .build()
        );
    }


    @Override
    public void deleteEvent(String eventName) {
        customizeEventRepository.deleteById(eventName);
    }


}
