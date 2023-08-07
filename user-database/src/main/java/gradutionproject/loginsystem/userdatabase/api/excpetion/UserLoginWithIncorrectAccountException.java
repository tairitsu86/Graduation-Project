package gradutionproject.loginsystem.userdatabase.api.excpetion;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserLoginWithIncorrectAccountException extends RuntimeException {
    private String username;

    @Override
    public String toString() {
        return String.format("Username '%s' login fail! Please check username and password!",username);
    }
}
