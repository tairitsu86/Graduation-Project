package graduationProject.IMUISystem.loginTracker.dto;

import lombok.Data;

@Data
public class LoginEventDto {
    private String username;
    private String fromPlatform;
    private String platformType;
    private String platformUserId;
}
