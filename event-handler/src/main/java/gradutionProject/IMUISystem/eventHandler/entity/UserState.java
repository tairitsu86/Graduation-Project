package gradutionProject.IMUISystem.eventHandler.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "description")
    private String description;

    @Column(name = "data")
    private String data;

    @Column(name = "parameters")
    private String parameters;
}
