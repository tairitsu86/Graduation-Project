package gradutionProject.IMUISystem.eventExecutor.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class NotifyVariableId {
    @Column(name = "event_name")
    private String eventName;
    @Column(name = "variable_name")
    private String variableName;
}
