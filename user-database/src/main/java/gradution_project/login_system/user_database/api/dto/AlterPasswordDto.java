package gradution_project.login_system.user_database.api.dto;

import lombok.Data;

@Data
public class AlterPasswordDto {
    private String username;
    private String newPassword;
}
