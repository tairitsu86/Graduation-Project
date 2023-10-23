package graduationProject.IMUISystem.eventHandler.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomizeEventVariable {
    private String displayNameTemplate;
    private String variableName;
}
