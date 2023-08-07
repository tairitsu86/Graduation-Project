package gradutionproject.loginsystem.userdatabase.api.dto;

import lombok.Data;

@Data
public class AlterUserDisplayNameDto {
    private String username;
    private String newUserDisplayName;
}
