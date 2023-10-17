package gradutionProject.IMUISystem.eventHandler.repository;

import gradutionProject.IMUISystem.eventHandler.dto.CustomizeEventDto;
import gradutionProject.IMUISystem.eventHandler.dto.UserStateDto;
import gradutionProject.IMUISystem.eventHandler.entity.CustomizeEvent;
import gradutionProject.IMUISystem.eventHandler.entity.IMUserData;

import java.util.List;

public interface RepositoryService {
    boolean isUserHaveState(IMUserData imUserData);
    void newUserStateDto(UserStateDto userStateDto);
    void removeUserState(IMUserData imUserData);
    UserStateDto getUserStateDto(IMUserData imUserData);
    boolean checkEventName(String eventName);
    List<String> getAllEvent();
    CustomizeEventDto getEventDto(String eventName);
    void newEvent(CustomizeEventDto customizeEventDto);
    void deleteEvent(String eventName);
}
