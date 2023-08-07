package gradution_project.login_system.user_database.api.excpetion;


import lombok.*;

@AllArgsConstructor
public class UserNotExistException extends RuntimeException{
    private String username;

    @Override
    public String toString() {
        return String.format("Username '%s' is not exist!",username);
    }
}
