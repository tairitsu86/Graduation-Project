package gradutionProject.IMUISystem.loginTracker.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IMUserData implements Serializable {
    @Column(name = "platform", nullable = false)
    private String platform;
    @Column(name = "userId", nullable = false)
    private String userId;
}
