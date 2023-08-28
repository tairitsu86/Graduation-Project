package gradutionProject.IMUISystem.eventHandler.dto;

import gradutionProject.IMUISystem.eventHandler.entity.APIData;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NewEventDto {
    private String eventName;
    private String description;
    private List<String> variables;
    private APIData apiData;
}
