package graduationProject.IMUISystem.eventExecutor.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("isGlobal")
    private boolean isGlobal;
    private Map<String,String> replaceValue;
}
