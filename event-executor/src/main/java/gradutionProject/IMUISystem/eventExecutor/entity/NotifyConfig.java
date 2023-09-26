package gradutionProject.IMUISystem.eventExecutor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notify_config")
public class NotifyConfig {
    @Id
    @Column(name = "event_name")
    private String eventName;
    @Column(name = "respond_template")
    private String respondTemplate;
    @OneToMany(mappedBy = "notifyData",cascade = CascadeType.ALL)
    private List<NotifyVariable> notifyVariables;
}
