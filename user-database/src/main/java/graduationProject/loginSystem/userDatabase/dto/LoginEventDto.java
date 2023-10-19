package graduationProject.loginSystem.userDatabase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginEventDto {
    private String username;
    private String fromPlatform;
    private String platformType;
    private String platformUserId;
}
