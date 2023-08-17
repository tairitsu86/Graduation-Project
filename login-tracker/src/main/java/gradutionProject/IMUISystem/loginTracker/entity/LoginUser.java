package gradutionProject.IMUISystem.loginTracker.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "[LOGIN_USER]")
public class LoginUser {
    @EmbeddedId
    private IMUserData imUserData;
    @Column(name = "username",nullable = false)
    private String username;
}
