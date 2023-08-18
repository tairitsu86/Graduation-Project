package gradutionProject.IMUISystem.webhookHandler.dto;

import gradutionProject.IMUISystem.webhookHandler.eventHandler.InstantMessagingPlatform;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IMUserData{
    private InstantMessagingPlatform platform;
    private String userId;
}
