package gradutionProject.IMUISystem.loginTracker.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Builder
@Entity
public class LoginUser {
    @EmbeddedId
    private IMUserData imUserData;
    @Column
    private String username;
}
