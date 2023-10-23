package graduationProject.IMUISystem.eventExecutor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuOption {
    private String displayName;
    private String nextEvent;
    private Map<String, Object> optionParameters;
}
