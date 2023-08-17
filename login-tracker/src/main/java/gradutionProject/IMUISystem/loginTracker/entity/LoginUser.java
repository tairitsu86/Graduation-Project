package gradutionProject.IMUISystem.loginTracker.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "LoginUser")
public class LoginUser {
    @EmbeddedId
    private IMUserData imUserData;
    @Column(name = "username",nullable = false)
    private String username;
}
