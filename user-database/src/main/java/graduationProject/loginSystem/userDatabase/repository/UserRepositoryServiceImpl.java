package graduationProject.loginSystem.userDatabase.repository;

import graduationProject.loginSystem.userDatabase.entity.User;
import graduationProject.loginSystem.userDatabase.controller.excpetion.UserLoginWithIncorrectAccountException;
import graduationProject.loginSystem.userDatabase.controller.excpetion.UserNotExistException;
import graduationProject.loginSystem.userDatabase.controller.excpetion.UsernameAlreadyExistException;
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
    public User.UserDto getUserData(String username) {
        checkUserExist(username);
        User user = userRepository.getReferenceById(username);
        return User.UserDto.builder()
                .username(user.getUsername())
                .userDisplayName(user.getUserDisplayName())
                .permission(user.getPermission())
                .build();
    }

    @Override
    public User.UserDto userLogin(String username, String password) {
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
