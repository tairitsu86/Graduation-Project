package gradutionProject.loginSystem.userDatabase.api.dto;

import lombok.Data;

@Data
public class AlterUserDataDto {
    private String newPassword;
    private String newUserDisplayName;
}
