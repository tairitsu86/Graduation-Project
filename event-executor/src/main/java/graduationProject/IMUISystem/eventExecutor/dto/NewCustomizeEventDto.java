package graduationProject.IMUISystem.eventExecutor.dto;

import graduationProject.IMUISystem.eventExecutor.entity.MenuOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewCustomizeEventDto {
    private String eventName;
    private String username;
    private String description;
    private List<MenuOption> menuOptions;
    private Map<String,Object> parameters;
}
