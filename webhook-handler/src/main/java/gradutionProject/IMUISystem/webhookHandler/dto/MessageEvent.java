package gradutionProject.IMUISystem.webhookHandler.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageEvent extends Event {
    private String message;
}
