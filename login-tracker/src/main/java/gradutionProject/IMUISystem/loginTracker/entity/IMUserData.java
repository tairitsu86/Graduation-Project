package gradutionProject.IMUISystem.loginTracker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IMUserData implements Serializable {
    private String platform;
    private String userId;
}
