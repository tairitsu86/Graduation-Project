package gradutionProject.IMUISystem.eventExecutor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notify_variable")
public class NotifyVariable {
    @EmbeddedId
    private NotifyVariableId notifyVariableId;

    @ManyToOne
    @JoinColumn(name = "event_name", insertable=false, updatable=false)
    private NotifyData notifyData;

    @Enumerated(EnumType.STRING)
    @Column(name = "notify_variable_type")
    private NotifyVariableType notifyVariableType;

    @Column(name = "json_path")
    private String jsonPath;
}
