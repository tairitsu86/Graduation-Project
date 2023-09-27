package gradutionProject.IMUISystem.eventHandler.handler;

import gradutionProject.IMUISystem.eventHandler.dto.ExecuteEventDto;
import gradutionProject.IMUISystem.eventHandler.dto.SendingEventDto;
import gradutionProject.IMUISystem.eventHandler.entity.IMUserData;
import gradutionProject.IMUISystem.eventHandler.entity.UserState;
import gradutionProject.IMUISystem.eventHandler.rabbitMQ.MQEventPublisher;
import gradutionProject.IMUISystem.eventHandler.repository.RepositoryService;
import gradutionProject.IMUISystem.eventHandler.request.RestRequestService;
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
    public void menuEvent(IMUserData imUserData,String username, String description, List<String> events, Map<String,String> metadata) {
        newUserState(imUserData,username,"MENU",events,metadata);
        StringBuilder message = new StringBuilder(description);
        for(int i=0;i<events.size();i++)
            message.append(String.format("\n[%d]%s",i,events.get(i)));
        sendMessage(imUserData,message.toString());
    }

    @Override
    public void exitEvent(IMUserData imUserData) {
        repositoryService.removeUserState(imUserData);
        sendMessage(imUserData,"EXIT Success!");
    }

    @Override
    public void loginOrSignUpEvent(IMUserData imUserData) {

    }

    @Override
    public void newUserEvent(IMUserData imUserData, String username, String eventName, Map<String, String> parameter) {
        if(!repositoryService.checkEventName(eventName)) return;
        Map<String, String> variables = new HashMap<>();
        List<String> eventVariables = repositoryService.getEventVariables(eventName);
        variables.putAll(parameter);
        List<String> data = new ArrayList<>();
        variables.put("PLATFORM",imUserData.getPlatform());
        variables.put("USER_ID",imUserData.getUserId());
        if(username!=null)
            variables.put("USERNAME",username);
        for (String variable:eventVariables) {
            if(variables.containsKey(variable)) continue;
            data.add(variable);
        }
//        if(data.isEmpty()&&username!=null){
//
//            return;
//        }
        newUserState(imUserData,username,eventName,data,variables);
//        sendMessage(imUserData,String.format("Please Enter %s!",data.get(0)));
    }

    @Override
    public void defaultMenu(IMUserData imUserData,String username) {
        menuEvent(imUserData,username,"Hello!\nWhat are you looking for?",repositoryService.getAllEvent(),null);
    }

    public void continueUserEvent(IMUserData imUserData, String message){
        UserState userState = repositoryService.getUserState(imUserData);
        if(userState.getEventName().equals("MENU")){
            int index;
            try{
                index = Integer.parseInt(message);
            }catch (NumberFormatException e){
                sendMessage(imUserData,"Please enter one number!");
                return;
            }
            if(index>=userState.getData().size()||index<0){
                sendMessage(imUserData,String.format("The number should in range [0~%d]!",userState.getData().size()-1));
                return;
            }
            newUserEvent(imUserData, userState.getUsername(), userState.getData().get(index),userState.getVariables());
            return;
        }
        userState.getVariables().put(userState.getData().get(0),message);
        userState.getData().remove(0);
        if(userState.getData().isEmpty()){
            executeEvent(userState.getEventName(), userState.getUsername(), userState.getVariables());
            repositoryService.removeUserState(imUserData);
            return;
        }
        sendMessage(imUserData,String.format("Please Enter %s!",userState.getData().get(0)));
        repositoryService.newUserState(userState);
    }

    public void executeEvent(String eventName, String executor, Map<String,String> variables){
        mqEventPublisher.publishExecuteEvent(
                ExecuteEventDto.builder()
                        .eventName(eventName)
                        .executor(executor)
                        .variables(variables)
                        .build()
        );
    }
    public void sendMessage(IMUserData imUserData,String message){
        mqEventPublisher.publishSendingEvent(SendingEventDto.builder()
                .imUserDataList(new ArrayList<>(){{add(imUserData);}})
                .message(message)
                .build()
        );
    }
    public void newUserState(IMUserData imUserData, String username, String eventName, List<String> data, Map<String,String> variables){
        repositoryService.newUserState(
                UserState.builder()
                        .imUserData(imUserData)
                        .username(username)
                        .eventName(eventName)
                        .data(data)
                        .variables(variables)
                        .build()
        );
    }
}