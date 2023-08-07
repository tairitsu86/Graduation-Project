package gradutionproject.loginsystem.userdatabase.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User {
    @Id
    private String username;
    @Column
    private String password;
    @Column
    private String userDisplayName;
    @Column
    private String jsonWebToken;
    @Column
    private Permission permission;
    static class UserData{
        private String username;
        private String userDisplayName;
        private Permission permission;
    }
    enum Permission{
        Admin,Normal,None;
    }
}


