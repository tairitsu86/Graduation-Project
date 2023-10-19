package graduationProject.loginSystem.userDatabase.repository;

import graduationProject.loginSystem.userDatabase.entity.User;

public interface UserRepositoryService {
    void addUser(String username, String password ,String userDisplayName);

    void alterUserDisplayName(String username,String newUserDisplayName);

    void alterPassword(String username,String newPassword);

    void deleteUser(String username);

    User.UserDto getUserData(String username);

    User.UserDto userLogin(String username, String password);

}
