package gradutionProject.IMUISystem.eventExecutor.entity;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotifyVariableId implements Serializable {
    @Column(name = "event_name")
    private String eventName;
    @Column(name = "variable_name")
    private String variableName;
}
