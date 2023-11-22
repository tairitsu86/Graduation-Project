package graduationProject.loginSystem.userDatabase.dto;

import lombok.Data;

@Data
public class UserLoginDto {
    private String username;
    private String password;
    private String fromPlatform;
    private String platformType;
    private String platformUserId;
}
