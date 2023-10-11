package gradutionProject.IMUISystem.eventHandler.dto;

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
    private List<String> variables;
    private CommConfigDto commConfigDto;
    private NotifyConfigDto notifyConfigDto;
}
