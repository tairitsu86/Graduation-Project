package graduationProject.IMUISystem.eventHandler.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IMUserData implements Serializable {
    @Column(name = "platform", nullable = false)
    private String platform;
    @Column(name = "user_id", nullable = false)
    private String userId;
}
