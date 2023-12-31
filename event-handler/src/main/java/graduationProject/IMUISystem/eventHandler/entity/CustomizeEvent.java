package graduationProject.IMUISystem.eventHandler.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "description_template")
    private String descriptionTemplate;

    @Column(name = "variables")
    private String variables;

}
