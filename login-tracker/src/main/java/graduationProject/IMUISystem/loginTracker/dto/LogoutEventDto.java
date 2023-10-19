package graduationProject.IMUISystem.loginTracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogoutEventDto {
    private String fromPlatform;
    private String platformType;
    private String platformUserId;
}
