package gradutionProject.IMUISystem.loginTracker.repository;

import gradutionProject.IMUISystem.loginTracker.entity.IMUserData;
import gradutionProject.IMUISystem.loginTracker.entity.LoginUser;

public interface LoginUserRepositoryService {
    LoginUser getLoginUser(String IM, String IMUserId);
    LoginUser getLoginUser(String username);

    void login(String IM, IMUserData imUserData);
    void logout(String IM, IMUserData imUserData);

}
