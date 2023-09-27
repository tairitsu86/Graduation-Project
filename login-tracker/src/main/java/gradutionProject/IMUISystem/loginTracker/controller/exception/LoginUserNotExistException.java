package gradutionProject.IMUISystem.loginTracker.controller.exception;

import gradutionProject.IMUISystem.loginTracker.entity.IMUserData;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginUserNotExistException extends RuntimeException {
    private IMUserData imUserData;

    @Override
    public String toString() {
        return String.format("IM platform [%s], IM user id [%s], didn't exist in login tracker!",imUserData.getPlatform(),imUserData.getUserId());
    }
}
