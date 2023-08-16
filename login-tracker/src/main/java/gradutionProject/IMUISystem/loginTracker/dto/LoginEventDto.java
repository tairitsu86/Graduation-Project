package gradutionProject.IMUISystem.loginTracker.dto;

import gradutionProject.IMUISystem.loginTracker.entity.IMUserData;
import lombok.Data;

@Data
public class LoginEventDto {
    private String IM;
    private IMUserData imUserData;
    private boolean isLogin;
}
