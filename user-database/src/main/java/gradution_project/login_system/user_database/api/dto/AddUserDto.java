package gradution_project.login_system.user_database.api.dto;

import lombok.Data;

@Data
public class AddUserDto {
    private String username;
    private String password;
    private String userDisplayName;
}