package gradutionProject.IMUISystem.eventExecutor.dto;

import gradutionProject.IMUISystem.eventExecutor.entity.MenuOption;
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
public class MenuConfigDto {
    private List<MenuOption> options;
    private Map<String,String> parameters;
}
