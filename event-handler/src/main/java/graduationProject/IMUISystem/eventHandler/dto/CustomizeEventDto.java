package graduationProject.IMUISystem.eventHandler.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private String description;
    private List<CustomizeEventVariable> variables;
    @JsonProperty("commConfig")
    private Object commConfigDto;
    @JsonProperty("respondConfig")
    private Object respondConfigDto;
}
