package gradutionProject.loginSystem.userDatabase.controller.excpetion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserLoginWithIncorrectAccountException extends RuntimeException {
    private String username;

    @Override
    public String toString() {
        return String.format("Username '%s' login fail! Please check username and password!",username);
    }
}
