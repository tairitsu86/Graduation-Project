package gradutionProject.IMUISystem.eventHandler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {
    private String username;
    private String password;
    private String fromPlatform;
    private String platformType;
    private String platformUserId;
}
