package gradutionProject.IMUISystem.eventHandler.dto;

import gradutionProject.IMUISystem.eventHandler.entity.IMUserData;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageEventDto {
    private String message;
    private IMUserData imUserData;
}
