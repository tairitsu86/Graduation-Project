package graduationProject.IMUISystem.eventExecutor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NotifyConfigDto {
    private String message;
    private List<String> usernameList;
    private List<String> groupList;
}
