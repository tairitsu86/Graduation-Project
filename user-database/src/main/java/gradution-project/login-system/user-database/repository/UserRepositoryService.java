package gradution_project.login_system.user_database.repository;

import gradution_project.login_system.user_database.entity.User;

public interface UserRepositoryService {
    void addUser(String username, String password ,String userDisplayName);

    void alterUserDisplayName(String username,String newUserDisplayName);

    void alterPassword(String username,String newPassword);

    void deleteUser(String username);

    User.UserDto userLogin(String username, String password, boolean keepLogin);

}
