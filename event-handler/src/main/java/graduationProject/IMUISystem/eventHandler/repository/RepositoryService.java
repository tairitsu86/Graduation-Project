package graduationProject.IMUISystem.eventHandler.repository;

import graduationProject.IMUISystem.eventHandler.dto.CustomizeEventDto;
import graduationProject.IMUISystem.eventHandler.dto.UserStateDto;
import graduationProject.IMUISystem.eventHandler.entity.IMUserData;

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
