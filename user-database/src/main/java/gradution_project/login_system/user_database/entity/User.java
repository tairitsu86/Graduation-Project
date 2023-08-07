package gradution_project.login_system.user_database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "[USER]")
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

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserDto {
        private String username;
        private String userDisplayName;
        private Permission permission;
    }
    enum Permission{
        Admin,Normal,None;
    }
}


