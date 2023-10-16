package gradutionProject.IMUISystem.eventExecutor.dto;

import gradutionProject.IMUISystem.eventExecutor.entity.RespondType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RespondConfigDto {
    private RespondType respondType;
    private Object respondData;
}
