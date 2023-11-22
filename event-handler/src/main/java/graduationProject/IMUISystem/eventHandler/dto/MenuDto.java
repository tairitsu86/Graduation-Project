package graduationProject.IMUISystem.eventHandler.dto;

import graduationProject.IMUISystem.eventHandler.entity.MenuOption;
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
public class MenuDto {
    private String menuName;

    private String description;

    private List<MenuOption> options;

    private Map<String,Object> parameters;
}
