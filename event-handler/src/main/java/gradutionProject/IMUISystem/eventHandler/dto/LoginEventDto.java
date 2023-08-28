package gradutionProject.IMUISystem.eventHandler.dto;

import gradutionProject.IMUISystem.eventHandler.entity.IMUserData;
import lombok.Data;

@Data
public class LoginEventDto {
    private IMUserData imUserData;
    private String username;
    private String state;
}
