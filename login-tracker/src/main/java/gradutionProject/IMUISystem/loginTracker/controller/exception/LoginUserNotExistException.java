package gradutionProject.IMUISystem.loginTracker.controller.exception;

import gradutionProject.IMUISystem.loginTracker.entity.IMUserData;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginUserNotExistException extends RuntimeException {
    private String user;

    @Override
    public String toString() {
        return String.format("User [%s], didn't exist in login tracker!",user);
    }
}
