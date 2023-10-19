package graduationProject.IMUISystem.eventExecutor.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SendingEventDto {
    private List<String> usernameList;
    private String message;
}
