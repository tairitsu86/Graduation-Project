package graduationProject.IMUISystem.eventHandler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomizeEventVariableDto {
    private String displayName;
    private String variableName;
}
