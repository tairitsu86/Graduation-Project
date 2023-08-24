package gradutionProject.IMUISystem.loginTracker.dto;

import gradutionProject.IMUISystem.loginTracker.entity.IMUserData;
import lombok.Data;

@Data
public class LoginEventDto {
    private String username;
    private String fromPlatform;
    private String platformType;
    private String platformUserId;
}
