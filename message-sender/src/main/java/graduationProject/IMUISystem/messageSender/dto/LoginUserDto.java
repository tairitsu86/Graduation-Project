package graduationProject.IMUISystem.messageSender.dto;


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
