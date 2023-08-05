package gradutionproject.loginsystem.userdatabase.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class User {
    @Id
    private String userName;
    @Column
    private String password;
    @Column
    private String userDisplayName;
    @Column
    private String jsonWebToken;
    @Column
    private Permission permission;
}
enum Permission{
    Admin,Normal,None;
}

