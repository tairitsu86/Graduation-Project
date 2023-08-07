package gradutionproject.loginsystem.userdatabase.repository;

import gradutionproject.loginsystem.userdatabase.api.excpetion.UserLoginWithIncorrectAccountException;
import gradutionproject.loginsystem.userdatabase.api.excpetion.UserNotExistException;
import gradutionproject.loginsystem.userdatabase.api.excpetion.UsernameAlreadyExistException;
import gradutionproject.loginsystem.userdatabase.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRepositoryServiceImpl implements UserRepositoryService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public void addUser(String username, String password , String userDisplayName) {
        checkUserNotExist(username);
        userRepository.save(
                User.builder()
                        .username(username)
                        .password(password)
                        .userDisplayName(userDisplayName)
                        .build()
        );
    }

    @Override
    public void alterUserDisplayName(String username, String newUserDisplayName) {
        User user = checkUserExist(username);
        user.setUserDisplayName(newUserDisplayName);
        userRepository.save(user);
    }

    @Override
    public void alterPassword(String username, String newPassword) {
        User user = checkUserExist(username);
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String username) {
        User user = checkUserExist(username);
        userRepository.delete(user);
    }

    @Override
    public User.UserDto userLogin(String username, String password, boolean keepLogin) {
        User user = checkUserExist(username);
        if(!user.getPassword().equals(password)) throw new UserLoginWithIncorrectAccountException(username);
        return User.UserDto.builder()
                .username(user.getUsername())
                .userDisplayName(user.getUserDisplayName())
                .permission(user.getPermission())
                .build();
    }

    private User checkUserExist(String username){
        User user = userRepository.getReferenceById(username);
        if(user==null) throw new UserNotExistException(username);
        return user;
    }

    private void checkUserNotExist(String username){
        User user = userRepository.getReferenceById(username);
        if(user!=null) throw new UsernameAlreadyExistException(username);
    }
}
