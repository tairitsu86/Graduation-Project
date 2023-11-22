package graduationProject.IMIGSystem.testConsole.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IMUserData{
    private String platform;
    private String userId;
}
