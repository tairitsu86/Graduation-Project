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
    @JoinTable(
            name = "user_state_data",
            joinColumns = {
                    @JoinColumn(name = "platform", referencedColumnName = "platform"),
                    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
            }
    )
    @Column(name = "data")
    @OrderColumn
    private List<String> data;

    @ElementCollection
    @JoinTable(
            name = "user_state_parameters",
            joinColumns = {
                    @JoinColumn(name = "platform", referencedColumnName = "platform"),
                    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
            }
    )
    @MapKeyColumn(name = "parameter_key")
    @Column(name = "parameter")
    private Map<String,String> parameters;
}
