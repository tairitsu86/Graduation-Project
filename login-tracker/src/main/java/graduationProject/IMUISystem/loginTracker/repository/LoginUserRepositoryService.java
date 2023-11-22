package graduationProject.IMUISystem.loginTracker.repository;

import graduationProject.IMUISystem.loginTracker.entity.LoginUser;
import graduationProject.IMUISystem.loginTracker.entity.IMUserData;


public interface LoginUserRepositoryService {
    LoginUser getLoginUser(String IMPlatform, String IMUserId);
    LoginUser getLoginUser(String username);

    void login(IMUserData imUserData, String username);
    void logout(IMUserData imUserData);

}
