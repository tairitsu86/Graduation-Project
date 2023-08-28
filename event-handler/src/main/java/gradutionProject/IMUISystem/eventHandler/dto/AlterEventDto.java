package gradutionProject.IMUISystem.eventHandler.dto;

import gradutionProject.IMUISystem.eventHandler.entity.APIData;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AlterEventDto {
    private String description;
    private List<String> variables;
    private APIData apiData;
}
