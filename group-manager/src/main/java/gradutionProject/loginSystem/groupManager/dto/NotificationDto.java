package gradutionProject.loginSystem.groupManager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NotificationDto {
    private final String from = "group-manager";
    private NotificationType notificationType;
    private String executor;
    private String groupId;
    private String username;
}
