package gradutionProject.IMUISystem.loginTracker.repository;

import gradutionProject.IMUISystem.loginTracker.entity.IMUserData;
import gradutionProject.IMUISystem.loginTracker.entity.LoginUser;

import java.util.List;

public interface LoginUserRepositoryService {
    LoginUser getLoginUser(String IMPlatform, String IMUserId);
    List<LoginUser> getLoginUser(String username);

    void login(IMUserData imUserData, String username);
    void logout(IMUserData imUserData);

}
