package graduationProject.IMUISystem.eventHandler.handler;

import graduationProject.IMUISystem.eventHandler.dto.*;
import graduationProject.IMUISystem.eventHandler.entity.CustomizeEventVariable;
import graduationProject.IMUISystem.eventHandler.entity.IMUserData;
import graduationProject.IMUISystem.eventHandler.entity.MenuOption;
import graduationProject.IMUISystem.eventHandler.rabbitMQ.MQEventPublisher;
import graduationProject.IMUISystem.eventHandler.repository.RepositoryService;
import graduationProject.IMUISystem.eventHandler.request.RestRequestService;
import graduationProject.IMUISystem.eventHandler.dto.*;
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
        if(message.trim().equalsIgnoreCase("EXIT")){
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
    public void menuEvent(IMUserData imUserData, String username, String description, List<MenuOption> menuOptions, Map<String,String> parameters) {

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
        menuEvent(
                imUserData,
                null,
                "Login or Sign up!"
                ,new ArrayList<>(){
                    {
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
                    }
                }
                ,null);
    }

    @Override
    public void newUserEvent(IMUserData imUserData, String username, String eventName, Map<String, String> parameter) {
        if(!repositoryService.checkEventName(eventName)) return;
        CustomizeEventDto customizeEventDto = repositoryService.getEventDto(eventName);

        Map<String, String> parameters = new HashMap<>(parameter);
        parameters.put("PLATFORM",imUserData.getPlatform());
        parameters.put("USER_ID",imUserData.getUserId());
        parameters.put("EVENT_NAME",eventName);
        if(username!=null)
            parameters.put("USERNAME",username);


        List<CustomizeEventVariable> data = new ArrayList<>();
        for (CustomizeEventVariable variable:customizeEventDto.getVariables()) {
            if(parameters.containsKey(variable.getVariableName())) continue;
            data.add(variable);
        }
        if(data.isEmpty()){
            executeEvent(eventName, parameters);
            return;
        }
        newUserState(imUserData, username, eventName, customizeEventDto.getDescription(), data, parameters);

        sendMessage(imUserData, String.format(customizeEventDto.getDescription()==null?"%s":customizeEventDto.getDescription(), data.get(0).getDisplayName()));
    }

    @Override
    public void defaultMenu(IMUserData imUserData,String username) {
        List<MenuOption> menuOptions = new ArrayList<>();
        List<String> events = repositoryService.getAllEvent();
        for (String event : events)
            menuOptions.add(
                    MenuOption.builder()
                            .displayName(event)
                            .nextEvent(event)
                            .build()
            );
        menuEvent(imUserData,username,"Hello!\nWhat are you looking for?", menuOptions,null);
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
        Map<String,String> parameters = new HashMap<>();
        if(userStateDto.getParameters()!=null)
            parameters.putAll(userStateDto.getParameters());
        if(option.getOptionParameters()!=null)
            parameters.putAll(option.getOptionParameters());
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
        variable = (CustomizeEventVariable)userStateDto.getData().get(0);
        sendMessage(imUserData,String.format(userStateDto.getDescription(), variable.getDisplayName()));
        repositoryService.newUserStateDto(userStateDto);
    }

    public void executeEvent(String eventName, Map<String,String> variables){
        switch (eventName){
            case "LOGIN" -> sendMessage(
                    IMUserData.builder()
                            .platform(variables.get("PLATFORM"))
                            .userId(variables.get("USER_ID"))
                            .build(),
                    restRequestService.login(
                            UserLoginDto.builder()
                                    .fromPlatform(variables.get("PLATFORM"))
                                    .platformType("Instant-Messaging")
                                    .platformUserId(variables.get("USER_ID"))
                                    .username(variables.get("USERNAME"))
                                    .password(variables.get("PASSWORD"))
                                    .build()
                    )
            );
            case "SIGN_UP" -> sendMessage(
                    IMUserData.builder()
                            .platform(variables.get("PLATFORM"))
                            .userId(variables.get("USER_ID"))
                            .build(),
                    restRequestService.signUp(
                            UserSignUpDto.builder()
                                    .userDisplayName(variables.get("USER_DISPLAY_NAME"))
                                    .username(variables.get("USERNAME"))
                                    .password(variables.get("PASSWORD"))
                                    .build()
                    )
            );
            default -> mqEventPublisher.publishExecuteEvent(
                    ExecuteEventDto.builder()
                            .eventName(eventName)
                            .executor(variables.get("USERNAME"))
                            .variables(variables)
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
    public void newUserState(IMUserData imUserData, String username, String eventName, String description, List<?> data, Map<String,String> variables){
        repositoryService.newUserStateDto(
                UserStateDto.builder()
                        .imUserData(imUserData)
                        .username(username)
                        .eventName(eventName)
                        .description(description)
                        .data(data)
                        .parameters(variables)
                        .build()
        );
    }
}
