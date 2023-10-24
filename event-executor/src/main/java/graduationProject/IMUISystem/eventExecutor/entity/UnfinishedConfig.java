package graduationProject.IMUISystem.eventExecutor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnfinishedConfig {
    private String nextEvent;
    private List<UnfinishedVariable> unfinishedVariables;
}
