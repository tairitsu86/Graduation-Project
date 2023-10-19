package graduationProject.IMUISystem.eventHandler.dto;

import graduationProject.IMUISystem.eventHandler.entity.IMUserData;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SendingEventDto {
    private List<IMUserData> imUserDataList;
    private List<String> usernameList;
    private String message;
}
