package gradutionProject.IMUISystem.eventHandler.request;

import gradutionProject.IMUISystem.eventHandler.dto.*;
import gradutionProject.IMUISystem.eventHandler.entity.IMUserData;

public interface RestRequestService {
    String getUsername(IMUserData imUserData);
    IMUserData getIMUserData(String username);

    String login(UserLoginDto userLoginDto);

    String signUp(UserSignUpDto userSignUpDto);

    void newCommConfig(String eventName, Object commConfigDto);

    void deleteCommConfig(String eventName);

    Object getCommConfig(String eventName);

    void newNotifyConfig(String eventName, Object notifyConfigDto);

    void deleteNotifyConfig(String eventName);

    Object getNotifyConfig(String eventName);


}
