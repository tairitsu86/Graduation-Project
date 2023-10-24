package graduationProject.IMUISystem.eventExecutor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnfinishedVariable {
    private String variableName;
    private String jsonPath;
}
