package gradutionProject.IMUISystem.eventHandler.handler;

import gradutionProject.IMUISystem.eventHandler.entity.CustomizeEvent;
import gradutionProject.IMUISystem.eventHandler.entity.IMUserData;

import java.util.List;
import java.util.Map;

public interface EventHandler {
    void menuEvent(IMUserData imUserData, String description, List<String> events, Map<String,String> metadata);
    void exitEvent(IMUserData imUserData);
    void event(IMUserData imUserData, String eventName, Map<String,String> parameter);
    void defaultMenu(IMUserData imUserData);
    boolean isUserInEvent(IMUserData imUserData, String message);

} 
