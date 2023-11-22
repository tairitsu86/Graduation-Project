package graduationProject.IMUISystem.eventHandler.request;

import graduationProject.IMUISystem.eventHandler.dto.UserLoginDto;
import graduationProject.IMUISystem.eventHandler.dto.UserSignUpDto;
import graduationProject.IMUISystem.eventHandler.entity.IMUserData;
import graduationProject.IMUISystem.eventHandler.dto.*;

public interface RestRequestService {
    String getUsername(IMUserData imUserData);
    IMUserData getIMUserData(String username);

    String login(UserLoginDto userLoginDto);

    String signUp(UserSignUpDto userSignUpDto);

    void newCommConfig(String eventName, Object commConfigDto);

    void deleteCommConfig(String eventName);

    Object getCommConfig(String eventName);

    void newRespondConfig(String eventName, Object respondConfigDto);

    void deleteRespondConfig(String eventName);

    Object getRespondConfig(String eventName);


}
