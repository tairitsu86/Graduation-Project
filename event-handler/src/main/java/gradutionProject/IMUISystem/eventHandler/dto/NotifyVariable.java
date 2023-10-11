package gradutionProject.IMUISystem.eventHandler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotifyVariable {
    private String eventName;
    private String variableName;
    private NotifyConfigDto notifyConfigDto;
    private NotifyVariableType notifyVariableType;
    private String jsonPath;
}
