package graduationProject.IMUISystem.eventExecutor.entity;

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
@Table(name = "communication_config")
public class CommConfig {
    @Id
    @Column(name = "event_name")
    private String eventName;

    @Enumerated(EnumType.STRING)
    @Column(name = "method_type")
    private MethodType methodType;

    @Column(name = "url_template")
    private String urlTemplate;

    @Column(name = "header_template")
    private String headerTemplate;

    @Column(name = "body_template")
    private String bodyTemplate;


}
