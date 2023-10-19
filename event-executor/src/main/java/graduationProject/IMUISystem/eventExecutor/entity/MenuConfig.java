package graduationProject.IMUISystem.eventExecutor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuConfig {
    private String descriptionTemplate;
    private String displayNameTemplate;
    private String nextEvent;
    private List<MenuVariable> variables;
    private Map<String,String> parameters;
}
