package graduationProject.IMUISystem.eventHandler.handler;

import graduationProject.IMUISystem.eventHandler.dto.*;
import graduationProject.IMUISystem.eventHandler.entity.CustomizeEventVariable;
import graduationProject.IMUISystem.eventHandler.entity.IMUserData;
import graduationProject.IMUISystem.eventHandler.entity.MenuOption;
import graduationProject.IMUISystem.eventHandler.rabbitMQ.MQEventPublisher;
import graduationProject.IMUISystem.eventHandler.repository.RepositoryService;
import graduationProject.IMUISystem.eventHandler.request.RestRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class EventHandlerImpl implements EventHandler{
    private final RepositoryService repositoryService;
    private final MQEventPublisher mqEventPublisher;
    private final RestRequestService restRequestService;

    @Override
    public void newMessage(IMUserData imUserData, String message) {
        if(message.equals("EXIT_MENU_EVENT_NOW")){
            exitEvent(imUserData);
            return;
        }

        if(repositoryService.isUserHaveState(imUserData)){
            continueUserEvent(imUserData,message);
            return;
        }

        String username = restRequestService.getUsername(imUserData);
        if(username==null){
            loginOrSignUpEvent(imUserData);
            return;
        }

        if(message.startsWith("/START_EVENT ")){
            newUserEvent(imUserData,username,message.substring(13),null);
            return;
        }

        defaultMenu(imUserData,username);
    }

    @Override
    public void menuEvent(IMUserData imUserData, String username, String description, List<MenuOption> menuOptions, Map<String,Object> parameters) {
        menuOptions.add(
                MenuOption.builder()
                        .nextEvent("EXIT")
                        .displayName("Exit")
                        .build()
        );
        newUserState(imUserData,username,"MENU",description, menuOptions, parameters);
        StringBuilder message = new StringBuilder(description);
        for(int i = 0; i< menuOptions.size(); i++)
            message.append(String.format("\n[%d]%s",i, menuOptions.get(i).getDisplayName()));
        sendMessage(imUserData,message.toString());
    }

    @Override
    public void exitEvent(IMUserData imUserData) {
        repositoryService.removeUserState(imUserData);
        sendMessage(imUserData,"EXIT Success!");
    }

    @Override
    public void loginOrSignUpEvent(IMUserData imUserData) {
        MenuDto menu = repositoryService.getMenuDto("LOGIN_OR_SIGN_UP_MENU");
        menuEvent(imUserData, null, menu.getDescription(), menu.getOptions(),menu.getParameters());
    }

    @Override
    public void newUserEvent(IMUserData imUserData, String username, String eventName, Map<String,Object> parameters) {
        if(!repositoryService.checkEventName(eventName)) return;
        CustomizeEventDto customizeEventDto = repositoryService.getEventDto(eventName);

        if(parameters==null)
            parameters = new HashMap<>();

        parameters.put("PLATFORM",imUserData.getPlatform());
        parameters.put("USER_ID",imUserData.getUserId());
        parameters.put("EVENT_NAME",eventName);
        if(username!=null)
            parameters.put("USERNAME",username);

        List<CustomizeEventVariable> data = new ArrayList<>();
        if(customizeEventDto.getVariables()!=null)
            for (CustomizeEventVariable variable:customizeEventDto.getVariables()) {
                if(parameters.containsKey(variable.getVariableName())) continue;
                data.add(
                        CustomizeEventVariable.builder()
                                .displayNameTemplate(variable.getDisplayNameTemplate())
                                .variableName(variable.getVariableName())
                                .build()
                );
            }

        if(data.isEmpty()){
            executeEvent(eventName, parameters);
            return;
        }
        newUserState(imUserData, username, eventName, customizeEventDto.getDescription(), data, parameters);

        sendMessage(imUserData, String.format(customizeEventDto.getDescription(), getDisplayName(data.get(0).getDisplayNameTemplate(), parameters)));
    }

    @Override
    public void defaultMenu(IMUserData imUserData,String username) {
        MenuDto menu = repositoryService.getMenuDto("DEFAULT_MENU");
        menuEvent(imUserData, username, menu.getDescription(), menu.getOptions(),menu.getParameters());
    }
    public String getDisplayName(String displayNameTemplate, Map<String, Object> parameters){
        for (String s:parameters.keySet())
            displayNameTemplate = displayNameTemplate.replace(String.format("${%s}",s),parameters.get(s).toString());
        return displayNameTemplate;
    }

    public void continueUserEvent(IMUserData imUserData, String message){
        UserStateDto userStateDto = repositoryService.getUserStateDto(imUserData);
        if (userStateDto.getEventName().equals("MENU")) {
            continueMenuEvent(userStateDto, imUserData, message);
        } else {
            continueCustomEvent(userStateDto, imUserData, message);
        }
    }

    public void continueMenuEvent(UserStateDto userStateDto, IMUserData imUserData, String message){
        int index;
        try{
            index = Integer.parseInt(message);
        }catch (NumberFormatException e){
            sendMessage(imUserData,"Please enter one number!");
            return;
        }
        if(index>=userStateDto.getData().size()||index<0){
            sendMessage(imUserData,String.format("The number should in range [0~%d]!",userStateDto.getData().size()-1));
            return;
        }
        if(!(userStateDto.getData().get(index) instanceof MenuOption option))
            throw new RuntimeException("userStateDto.getData().get(index) not a MenuOption!");

        repositoryService.removeUserState(imUserData);
        Map<String,Object> parameters = new HashMap<>();
        if(userStateDto.getParameters()!=null)
            parameters.putAll(userStateDto.getParameters());
        if(option.getOptionParameters()!=null)
            parameters.putAll(option.getOptionParameters());

        if(option.getNextEvent().equals("EXIT"))
            exitEvent(imUserData);
        else
            newUserEvent(imUserData, userStateDto.getUsername(), option.getNextEvent(), parameters);
    }

    public void continueCustomEvent(UserStateDto userStateDto, IMUserData imUserData, String message){
        if(!(userStateDto.getData().get(0) instanceof CustomizeEventVariable variable))
            throw new RuntimeException("userStateDto.getData().get(0) not a CustomizeEventVariable!");

        userStateDto.getParameters().put(variable.getVariableName(), message);
        userStateDto.getData().remove(0);
        if(userStateDto.getData().isEmpty()){
            executeEvent(userStateDto.getEventName(), userStateDto.getParameters());
            repositoryService.removeUserState(imUserData);
            return;
        }

        if(!(userStateDto.getData().get(0) instanceof CustomizeEventVariable nextVar))
            throw new RuntimeException("userStateDto.getData().get(0) not a CustomizeEventVariable!");

        sendMessage(imUserData,String.format(userStateDto.getDescription(), getDisplayName(nextVar.getDisplayNameTemplate(), userStateDto.getParameters())));
        repositoryService.newUserStateDto(userStateDto);
    }

    public void executeEvent(String eventName, Map<String,Object> parameters){
        switch (eventName){
            case "LOGIN" -> sendMessage(
                    IMUserData.builder()
                            .platform(parameters.get("PLATFORM").toString())
                            .userId(parameters.get("USER_ID").toString())
                            .build(),
                    restRequestService.login(
                            UserLoginDto.builder()
                                    .fromPlatform(parameters.get("PLATFORM").toString())
                                    .platformType("Instant-Messaging")
                                    .platformUserId(parameters.get("USER_ID").toString())
                                    .username(parameters.get("USERNAME").toString())
                                    .password(parameters.get("PASSWORD").toString())
                                    .build()
                    )
            );
            case "SIGN_UP" -> sendMessage(
                    IMUserData.builder()
                            .platform(parameters.get("PLATFORM").toString())
                            .userId(parameters.get("USER_ID").toString())
                            .build(),
                    restRequestService.signUp(
                            UserSignUpDto.builder()
                                    .userDisplayName(parameters.get("USER_DISPLAY_NAME").toString())
                                    .username(parameters.get("USERNAME").toString())
                                    .password(parameters.get("PASSWORD").toString())
                                    .build()
                    )
            );
            default -> mqEventPublisher.publishExecuteEvent(
                    ExecuteEventDto.builder()
                            .eventName(eventName)
                            .executor(parameters.get("USERNAME").toString())
                            .parameters(parameters)
                            .build()
            );
        }
    }
    public void sendMessage(IMUserData imUserData,String message){
        mqEventPublisher.publishSendingEvent(SendingEventDto.builder()
                .imUserDataList(new ArrayList<>(){{add(imUserData);}})
                .message(message)
                .build()
        );
    }
    public void newUserState(IMUserData imUserData, String username, String eventName, String description, List<?> data, Map<String,Object> parameters){
        repositoryService.newUserStateDto(
                UserStateDto.builder()
                        .imUserData(imUserData)
                        .username(username)
                        .eventName(eventName)
                        .description(description)
                        .data(data)
                        .parameters(parameters)
                        .build()
        );
    }
}
