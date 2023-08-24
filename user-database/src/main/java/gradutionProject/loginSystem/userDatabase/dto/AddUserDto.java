package gradutionProject.loginSystem.userDatabase.dto;

import lombok.Data;

@Data
public class AddUserDto {
    private String username;
    private String password;
    private String userDisplayName;

    public static void main(String[] args) {
        System.out.println(System.getenv("USER-DATABASE-DATABASENAME"));
    }
}
