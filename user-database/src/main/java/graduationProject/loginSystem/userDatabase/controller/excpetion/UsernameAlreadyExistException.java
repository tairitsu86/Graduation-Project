package graduationProject.loginSystem.userDatabase.controller.excpetion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UsernameAlreadyExistException extends RuntimeException{
    private String username;

    @Override
    public String toString() {
        return String.format("Username '%s' is already exist!",username);
    }
}
