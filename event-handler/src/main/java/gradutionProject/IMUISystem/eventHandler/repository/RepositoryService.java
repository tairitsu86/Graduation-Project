package gradutionProject.IMUISystem.eventHandler.repository;

import gradutionProject.IMUISystem.eventHandler.entity.APIData;
import gradutionProject.IMUISystem.eventHandler.entity.CustomizeEvent;
import gradutionProject.IMUISystem.eventHandler.entity.IMUserData;
import gradutionProject.IMUISystem.eventHandler.entity.UserState;

import java.util.List;

public interface RepositoryService {
    boolean isUserHaveState(IMUserData imUserData);
    void newUserState(UserState userState);
    void removeUserState(IMUserData imUserData);
    UserState getUserState(IMUserData imUserData);

    boolean checkEventName(String eventName);
    List<String> getEventVariables(String eventName);
    APIData getAPIData(String eventName);
    List<String> getAllEvent();

    CustomizeEvent getEvent(String eventName);
    void newEvent(CustomizeEvent customizeEvent);
    void alterEvent(String eventName,String description, APIData apiData,List<String> variables);
    void deleteEvent(String eventName);
}
