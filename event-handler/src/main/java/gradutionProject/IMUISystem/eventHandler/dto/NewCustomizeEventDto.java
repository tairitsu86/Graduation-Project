package gradutionProject.IMUISystem.eventHandler.dto;

import gradutionProject.IMUISystem.eventHandler.entity.Option;
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
    private List<Option> options;
    private Map<String,String> parameters;
}
