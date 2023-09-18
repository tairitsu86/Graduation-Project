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
@Embeddable
@Entity
@Table(name = "mq_data")
public class MQData {
    @Id
    @Column(name = "event_name")
    private String eventName;
    @Column(name = "topic")
    private String topic;
    @Column(name = "event_body_template")
    private String eventBodyTemplate;
}
