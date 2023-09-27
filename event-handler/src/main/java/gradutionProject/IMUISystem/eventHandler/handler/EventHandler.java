package gradutionProject.IMUISystem.eventHandler.handler;

import gradutionProject.IMUISystem.eventHandler.entity.IMUserData;

import java.util.List;
import java.util.Map;

public interface EventHandler {

    void newMessage(IMUserData imUserData,String message);
    void menuEvent(IMUserData imUserData,String username, String description, List<String> events, Map<String,String> metadata);
    void exitEvent(IMUserData imUserData);
    void loginOrSignUpEvent(IMUserData imUserData);
    void newUserEvent(IMUserData imUserData, String username, String eventName, Map<String,String> parameter);
    void defaultMenu(IMUserData imUserData,String username);
    void continueUserEvent(IMUserData imUserData, String message);


} 
