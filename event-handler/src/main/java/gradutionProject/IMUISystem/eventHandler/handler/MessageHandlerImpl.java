package gradutionProject.IMUISystem.eventHandler.handler;

import gradutionProject.IMUISystem.eventHandler.entity.IMUserData;
import gradutionProject.IMUISystem.eventHandler.repository.RepositoryService;
import gradutionProject.IMUISystem.eventHandler.request.RestRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MessageHandlerImpl implements MessageHandler{
    private final RepositoryService repositoryService;
    private final EventHandler eventHandler;
    private final RestRequestService restRequestService;

    @Override
    public void checkUser(IMUserData imUserData, String message) {
        if(message.trim().equalsIgnoreCase("EXIT")){
            eventHandler.exitEvent(imUserData);
            return;
        }
        if(eventHandler.isUserInEvent(imUserData,message)) return;
        String username = restRequestService.getUsername(imUserData);
        if(username==null){
            eventHandler.menuEvent(
                    imUserData
                    ,null
                    ,"Login or Sign up!"
                    ,new ArrayList<>(){{add("LOGIN");add("SIGN_UP");}}
                    ,null);
            return;
        }
        if(message.startsWith("/START_EVENT ")){
            eventHandler.event(imUserData,username,message.substring(13),null);
            return;
        }

        eventHandler.defaultMenu(imUserData,username);
    }



}
