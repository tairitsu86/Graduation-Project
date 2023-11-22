package gradutionProject.IMUISystem.webhookHandler.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageEventDto{
    private String message;
    private IMUserData imUserData;
}
