package graduationProject.IMUISystem.eventHandler.repository;

import graduationProject.IMUISystem.eventHandler.dto.CustomizeEventDto;
import graduationProject.IMUISystem.eventHandler.dto.MenuDto;
import graduationProject.IMUISystem.eventHandler.dto.UserStateDto;
import graduationProject.IMUISystem.eventHandler.entity.IMUserData;
import graduationProject.IMUISystem.eventHandler.entity.Menu;
import graduationProject.IMUISystem.eventHandler.entity.MenuOption;

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
    MenuDto getMenuDto(String menuName);
    void setMenuDto(MenuDto menuDto);
    void addOptionToDefaultMenu(MenuOption menuOption);
    void removeOptionFromDefaultMenu(MenuOption menuOption);
}
