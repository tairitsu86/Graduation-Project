package gradutionProject.IMUISystem.eventHandler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommConfigDto {
    private MethodType methodType;
    private String urlTemplate;
    private String bodyTemplate;
}
