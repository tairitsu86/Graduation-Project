package gradutionProject.IMUISystem.loginTracker.repository;

import gradutionProject.IMUISystem.loginTracker.entity.IMUserData;
import gradutionProject.IMUISystem.loginTracker.entity.LoginUser;


public interface LoginUserRepositoryService {
    LoginUser getLoginUser(String IMPlatform, String IMUserId);
    LoginUser getLoginUser(String username);

    void login(IMUserData imUserData, String username);
    void logout(IMUserData imUserData);

}
