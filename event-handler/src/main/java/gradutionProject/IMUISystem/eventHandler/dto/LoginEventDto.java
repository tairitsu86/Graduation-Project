package gradutionProject.IMUISystem.eventHandler.dto;

import gradutionProject.IMUISystem.eventHandler.entity.IMUserData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginEventDto {
    private IMUserData imUserData;
    private String username;
    private String state;
}
