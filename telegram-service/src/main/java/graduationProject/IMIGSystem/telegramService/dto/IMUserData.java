package graduationProject.IMIGSystem.telegramService.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IMUserData{
    private String platform;
    private String userId;
}
