package gradutionProject.IMUISystem.messageSender.dto;

import gradutionProject.IMUISystem.messageSender.instantMessagingPlatform.InstantMessagingPlatform;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IMUserData {
    private InstantMessagingPlatform platform;
    private String userId;
}
