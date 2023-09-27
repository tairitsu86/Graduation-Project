package gradutionProject.IMUISystem.eventHandler.request;

import gradutionProject.IMUISystem.eventHandler.dto.AddUserDto;
import gradutionProject.IMUISystem.eventHandler.dto.CommConfigDto;
import gradutionProject.IMUISystem.eventHandler.dto.LoginEventDto;
import gradutionProject.IMUISystem.eventHandler.entity.IMUserData;
import gradutionProject.IMUISystem.eventHandler.dto.NotifyConfigDto;

public interface RestRequestService {
    String getUsername(IMUserData imUserData);

    void login(LoginEventDto loginEventDto);

    void signUp(AddUserDto addUserDto);

    void newCommConfig(CommConfigDto commConfigDto);

    void deleteCommConfig(String eventName);

    void newNotifyConfig(NotifyConfigDto notifyConfigDto);

    void deleteNotifyConfig(String eventName);


}
