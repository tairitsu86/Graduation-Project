package graduationProject.IMUISystem.eventExecutor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExecuteEventDto {
    private String eventName;
    private String executor;
    private Map<String,String> variables;
}
