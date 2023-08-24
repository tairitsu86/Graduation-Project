package gradutionProject.IMUISystem.messageSender.dto;

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
