package gradutionProject.IMUISystem.eventExecutor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuVariable {
    private String variableName;
    private String jsonPath;
    private boolean isGlobal;
    private Map<String,String> replaceValue;
}
