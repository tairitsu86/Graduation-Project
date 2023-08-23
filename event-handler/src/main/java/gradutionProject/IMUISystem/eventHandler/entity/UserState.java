package gradutionProject.IMUISystem.eventHandler.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_state")
public class UserState {
    @EmbeddedId
    private IMUserData imUserData;
    @Column(name = "username")
    private String username;
    @Column(name = "event_name")
    private String eventName;
    @ElementCollection
    private List<String> data;
    @ElementCollection
    private Map<String,String> variables;
}
