package gradutionProject.IMUISystem.loginTracker.dto;

import gradutionProject.IMUISystem.loginTracker.entity.IMUserData;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetIMDataDto {
    private IMUserData imUserData;
}
