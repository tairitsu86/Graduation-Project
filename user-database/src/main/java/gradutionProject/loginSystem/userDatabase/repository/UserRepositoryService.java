package gradutionProject.loginSystem.userDatabase.repository;

import gradutionProject.loginSystem.userDatabase.entity.User;

public interface UserRepositoryService {
    void addUser(String username, String password ,String userDisplayName);

    void alterUserDisplayName(String username,String newUserDisplayName);

    void alterPassword(String username,String newPassword);

    void deleteUser(String username);

    User.UserDto userLogin(String username, String password, boolean keepLogin);

}
