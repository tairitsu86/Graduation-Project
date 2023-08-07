package gradution_project.login_system.user_database.api.dto;

import lombok.Data;

@Data
public class AlterUserDisplayNameDto {
    private String username;
    private String newUserDisplayName;
}
