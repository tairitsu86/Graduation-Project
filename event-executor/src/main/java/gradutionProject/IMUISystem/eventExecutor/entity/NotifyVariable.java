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
@IdClass(NotifyVariableId.class)
public class NotifyVariable {
    
    @Id
    @Column(name = "event_name")
    private String eventName;

    @Id
    @Column(name = "variable_name")
    private String variableName;

    @ManyToOne
    @JoinColumn(name = "event_name", insertable=false, updatable=false)
    private NotifyConfig notifyConfig;

    @Enumerated(EnumType.STRING)
    @Column(name = "notify_variable_type")
    private NotifyVariableType notifyVariableType;

    @Column(name = "json_path")
    private String jsonPath;
}
