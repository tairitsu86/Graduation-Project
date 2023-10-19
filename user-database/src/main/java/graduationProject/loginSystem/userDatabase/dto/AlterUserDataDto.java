package graduationProject.loginSystem.userDatabase.dto;

import lombok.Data;

@Data
public class AlterUserDataDto {
    private String newPassword;
    private String newUserDisplayName;
}
