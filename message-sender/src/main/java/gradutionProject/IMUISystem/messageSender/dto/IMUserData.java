package gradutionProject.IMUISystem.messageSender.dto;

import gradutionProject.IMUISystem.messageSender.instantMessagingPlatform.InstantMessagingPlatform;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IMUserData {
    private InstantMessagingPlatform platform;
    private String userId;
}
