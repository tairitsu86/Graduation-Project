package gradution_project.login_system.user_database.api.dto;

import lombok.Data;

@Data
public class AlterUserDataDto {
    private String newPassword;
    private String newUserDisplayName;
}
