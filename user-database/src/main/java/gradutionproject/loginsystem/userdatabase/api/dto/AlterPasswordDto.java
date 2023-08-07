package gradutionproject.loginsystem.userdatabase.api.dto;

import lombok.Data;

@Data
public class AlterPasswordDto {
    private String username;
    private String newPassword;
}
