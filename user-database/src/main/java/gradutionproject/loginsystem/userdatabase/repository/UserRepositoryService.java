package gradutionproject.loginsystem.userdatabase.repository;

import gradutionproject.loginsystem.userdatabase.entity.User;

public interface UserRepositoryService {
    void addUser(String userDisplayName,String password);

    void alterUser(String username,User alterUser);

    void alterUserDisplayName(String username,String newUserDisplayName);

    void alterPassword(String username,String newPassword);

    void deleteUser(String username);

    String userLogin(String username,String password,boolean keepLogin);

}
