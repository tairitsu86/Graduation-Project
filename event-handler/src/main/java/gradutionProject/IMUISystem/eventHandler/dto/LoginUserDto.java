package gradutionProject.IMUISystem.eventHandler.dto;

import gradutionProject.IMUISystem.eventHandler.entity.IMUserData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginUserDto {
    private IMUserData imUserData;
    private String username;
}
