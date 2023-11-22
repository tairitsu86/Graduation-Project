package graduationProject.loginSystem.groupManager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateGroupDto {
    private String groupName;
    private String description;
    private boolean visible;
    private boolean joinActively;
}
