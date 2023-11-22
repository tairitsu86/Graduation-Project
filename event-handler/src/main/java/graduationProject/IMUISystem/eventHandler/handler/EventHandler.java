package graduationProject.IMUISystem.eventHandler.handler;

import graduationProject.IMUISystem.eventHandler.entity.IMUserData;
import graduationProject.IMUISystem.eventHandler.entity.MenuOption;

import java.util.List;
import java.util.Map;

public interface EventHandler {

    void newMessage(IMUserData imUserData, String message);
    void menuEvent(IMUserData imUserData, String username, String description, List<MenuOption> menuOptions,Map<String,Object> parameters);
    void exitEvent(IMUserData imUserData);
    void loginOrSignUpEvent(IMUserData imUserData);
    void newUserEvent(IMUserData imUserData, String username, String eventName, Map<String,Object> parameters);
    void defaultMenu(IMUserData imUserData,String username);
    void continueUserEvent(IMUserData imUserData, String message);


} 
