package gradution_project.login_system.user_database.api.excpetion;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserNotExistException extends RuntimeException{
    private String username;

    @Override
    public String toString() {
        return String.format("Username '%s' is not exist!",username);
    }
}
