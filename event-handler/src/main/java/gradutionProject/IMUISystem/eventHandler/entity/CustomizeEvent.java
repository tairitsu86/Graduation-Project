package gradutionProject.IMUISystem.eventHandler.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="customize_event")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CustomizeEvent{
    @Id
    @Column(name = "event_name")
    private String eventName;
    @Column(name = "description")
    private String description;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "customize_event_variables", joinColumns = @JoinColumn(name = "event_name"),uniqueConstraints = {@UniqueConstraint(columnNames = {"event_name", "variable"})})
    @Column(name = "variable")
    private List<String> variables;
}
