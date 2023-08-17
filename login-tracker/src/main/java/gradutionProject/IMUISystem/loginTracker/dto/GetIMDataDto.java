package gradutionProject.IMUISystem.loginTracker.dto;

import gradutionProject.IMUISystem.loginTracker.entity.IMUserData;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetIMDataDto {
    private List<IMUserData> imUserDataList;
}
