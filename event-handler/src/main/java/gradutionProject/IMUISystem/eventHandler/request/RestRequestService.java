package gradutionProject.IMUISystem.eventHandler.request;

import gradutionProject.IMUISystem.eventHandler.dto.*;
import gradutionProject.IMUISystem.eventHandler.entity.IMUserData;

public interface RestRequestService {
    String getUsername(IMUserData imUserData);

    String login(UserLoginDto userLoginDto);

    String signUp(UserSignUpDto userSignUpDto);

    void newCommConfig(String eventName, CommConfigDto commConfigDto);

    void deleteCommConfig(String eventName);

    void newNotifyConfig(String eventName,NotifyConfigDto notifyConfigDto);

    void deleteNotifyConfig(String eventName);


}
