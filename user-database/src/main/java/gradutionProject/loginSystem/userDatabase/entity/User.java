package gradutionProject.loginSystem.userDatabase.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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
    @Enumerated(EnumType.ORDINAL)
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
    public enum Permission{
        Admin,Normal,None;
    }
}


