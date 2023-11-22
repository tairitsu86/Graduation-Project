package graduationProject.IMUISystem.messageSender.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IMUserData {
    private String platform;
    private String userId;
}
