package graduationProject.IMUISystem.eventExecutor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotifyVariable {
    private String variableName;
    private String jsonPath;
    private Map<String,String> replaceValue;
    private String startFormat;
    private String middleFormat;
    private String endFormat;

}
