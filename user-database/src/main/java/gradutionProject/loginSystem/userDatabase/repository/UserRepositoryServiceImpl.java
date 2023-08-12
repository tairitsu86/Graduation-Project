package gradutionProject.loginSystem.userDatabase.repository;

import gradutionProject.loginSystem.userDatabase.api.excpetion.UserLoginWithIncorrectAccountException;
import gradutionProject.loginSystem.userDatabase.api.excpetion.UserNotExistException;
import gradutionProject.loginSystem.userDatabase.api.excpetion.UsernameAlreadyExistException;
import gradutionProject.loginSystem.userDatabase.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRepositoryServiceImpl implements UserRepositoryService{
    private final UserRepository userRepository;

    @Override
    public void addUser(String username, String password , String userDisplayName) {
        if(userRepository.existsById(username))
            throw new UsernameAlreadyExistException(username);
        userRepository.save(
                User.builder()
                        .username(username)
                        .password(password)
                        .userDisplayName(userDisplayName)
                        .permission(User.Permission.Normal)
                        .build()
        );
    }

    @Override
    public void alterUserDisplayName(String username, String newUserDisplayName) {
        checkUserExist(username);
        User user = userRepository.getReferenceById(username);
        user.setUserDisplayName(newUserDisplayName);
        userRepository.save(user);
    }

    @Override
    public void alterPassword(String username, String newPassword) {
        checkUserExist(username);
        User user = userRepository.getReferenceById(username);
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

    @Override
    public User.UserDto userLogin(String username, String password, boolean keepLogin) {
        if(!userRepository.existsById(username))
            throw new UserLoginWithIncorrectAccountException(username);
        User user = userRepository.getReferenceById(username);
        if(!user.getPassword().equals(password)) throw new UserLoginWithIncorrectAccountException(username);
        return User.UserDto.builder()
                .username(user.getUsername())
                .userDisplayName(user.getUserDisplayName())
                .permission(user.getPermission())
                .build();
    }

    public void checkUserExist(String username){
        if(!userRepository.existsById(username))
            throw new UserNotExistException(username);
    }
}
