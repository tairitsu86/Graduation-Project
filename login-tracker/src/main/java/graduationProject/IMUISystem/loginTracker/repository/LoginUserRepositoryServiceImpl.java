package graduationProject.IMUISystem.loginTracker.repository;

import graduationProject.IMUISystem.loginTracker.entity.LoginUser;
import graduationProject.IMUISystem.loginTracker.controller.exception.LoginUserNotExistException;
import graduationProject.IMUISystem.loginTracker.entity.IMUserData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LoginUserRepositoryServiceImpl implements LoginUserRepositoryService{
    private final LoginUserRepository loginUserRepository;
    @Override
    public LoginUser getLoginUser(String IMPlatform, String IMUserId) {
        IMUserData id = IMUserData.builder()
                .platform(IMPlatform)
                .userId(IMUserId)
                .build();
        if(!loginUserRepository.existsById(id))
            throw new LoginUserNotExistException(id.toString());
        return loginUserRepository.findById(id).get();
    }

    @Override
    public LoginUser getLoginUser(String username) {
        if(!loginUserRepository.existsByUsername(username))
            throw new LoginUserNotExistException(username);
        return loginUserRepository.findByUsername(username);
    }

    @Override
    public void login(IMUserData imUserData, String username) {
        loginUserRepository.save(
                LoginUser.builder()
                        .imUserData(imUserData)
                        .username(username)
                        .build()
        );
    }

    @Override
    public void logout(IMUserData imUserData) {
        loginUserRepository.deleteById(imUserData);
    }
}
