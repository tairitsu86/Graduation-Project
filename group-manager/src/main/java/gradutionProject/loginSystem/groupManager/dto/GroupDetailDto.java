package gradutionProject.loginSystem.groupManager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GroupDetailDto {
    private String groupId;
    private String groupName;
    private String description;
}
