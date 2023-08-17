package gradutionProject.IMUISystem.loginTracker.dto;

import gradutionProject.IMUISystem.loginTracker.entity.IMUserData;
import lombok.Data;

@Data
public class LoginEventDto {
    private IMUserData imUserData;
    private String username;
    private String state;
}
