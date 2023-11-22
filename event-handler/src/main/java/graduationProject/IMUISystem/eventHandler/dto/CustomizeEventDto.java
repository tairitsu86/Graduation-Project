package graduationProject.IMUISystem.eventHandler.dto;

import graduationProject.IMUISystem.eventHandler.entity.CustomizeEventVariable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomizeEventDto {
    private String eventName;
    private String descriptionTemplate;
    private List<CustomizeEventVariable> variables;
    private Object commConfig;
    private Object respondConfig;
}
