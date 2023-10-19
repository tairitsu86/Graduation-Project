package graduationProject.loginSystem.userDatabase.dto;

import lombok.Data;

@Data
public class AddUserDto {
    private String username;
    private String password;
    private String userDisplayName;
}
