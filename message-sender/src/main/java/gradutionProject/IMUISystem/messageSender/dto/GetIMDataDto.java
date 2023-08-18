package gradutionProject.IMUISystem.messageSender.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetIMDataDto {
    private List<IMUserData> imUserDataList;
}
