package gradutionProject.IMUISystem.eventHandler.dto;

import gradutionProject.IMUISystem.eventHandler.entity.IMUserData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageEventDto {
    private String message;
    private IMUserData imUserData;
}
