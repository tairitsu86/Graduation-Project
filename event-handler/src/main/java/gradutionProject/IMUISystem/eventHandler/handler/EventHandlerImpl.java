package gradutionProject.IMUISystem.eventHandler.handler;

import gradutionProject.IMUISystem.eventHandler.dto.SendingEventDto;
import gradutionProject.IMUISystem.eventHandler.entity.CustomizeEvent;
import gradutionProject.IMUISystem.eventHandler.entity.IMUserData;
import gradutionProject.IMUISystem.eventHandler.entity.UserState;
import gradutionProject.IMUISystem.eventHandler.rabbitMQ.MQEventPublisher;
import gradutionProject.IMUISystem.eventHandler.repository.RepositoryService;
import gradutionProject.IMUISystem.eventHandler.repository.UserStateRepository;
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
    public void menuEvent(IMUserData imUserData, String description, List<String> events, Map<String,String> metadata) {
        repositoryService.newUserState(
                UserState.builder()
                        .imUserData(imUserData)
                        .eventName("MENU")
                        .data(events)
                        .variables(metadata)
                        .build()
        );
        String message = description;
        for(int i=0;i<events.size();i++)
            message.concat(String.format("\n[%d]%s",i,events.get(i)));

        mqEventPublisher.publishSendingEvent(SendingEventDto.builder()
                .imUserDataList(new ArrayList<>(){{add(imUserData);}})
                .message(message)
                .build()
        );
    }

    @Override
    public void exitEvent(IMUserData imUserData) {
        repositoryService.removeUserState(imUserData);
        mqEventPublisher.publishSendingEvent(SendingEventDto.builder()
                .imUserDataList(new ArrayList<>(){{add(imUserData);}})
                .message("EXIT")
                .build()
        );
    }

    @Override
    public void event(IMUserData imUserData, String eventName, Map<String, String> parameter) {
        if(!repositoryService.checkEventName(eventName)) return;
        Map<String, String> askValue = new HashMap<>();
        List<String> variables = repositoryService.getEventVariables(eventName);
        askValue.putAll(parameter);
        List<String> data = new ArrayList<>();
        for (String variable:variables) {
            if(askValue.containsKey(variable)) continue;
            data.add(variable);
        }
        if(data.isEmpty()){
            restRequestService.sendEventRequest(
                    repositoryService.getAPIData(eventName),
                    askValue
            );
            return;
        }
        repositoryService.newUserState(
                UserState.builder()
                        .imUserData(imUserData)
                        .data(data)
                        .eventName(eventName)
                        .variables(askValue)
                        .build()
        );
        mqEventPublisher.publishSendingEvent(SendingEventDto.builder()
                .imUserDataList(new ArrayList<>(){{add(imUserData);}})
                .message(String.format("Please Enter %s!",data.get(0)))
                .build()
        );
    }

    @Override
    public void defaultMenu(IMUserData imUserData) {
        menuEvent(imUserData,"Hello!\nWhat are you looking for?",repositoryService.getAllEvent(),null);
    }

    @Override
    public boolean isUserInEvent(IMUserData imUserData, String message) {
        if(!repositoryService.isUserHaveState(imUserData)) return false;
        UserState userState = repositoryService.getUserState(imUserData);
        userState.getVariables().put(userState.getData().get(0),message);
        userState.getData().remove(0);
        mqEventPublisher.publishSendingEvent(SendingEventDto.builder()
                .imUserDataList(new ArrayList<>(){{add(imUserData);}})
                .message(String.format("Please Enter %s!",userState.getData().get(0)))
                .build()
        );
        repositoryService.newUserState(userState);
        return true;
    }

}
