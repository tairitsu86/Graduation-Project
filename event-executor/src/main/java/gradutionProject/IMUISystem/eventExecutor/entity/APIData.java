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
@Table(name = "api_data")
public class APIData {
    @Id
    @Column(name = "event_name")
    private String eventName;
    @Column(name = "url_template")
    private String urlTemplate;
    @Enumerated(EnumType.STRING)
    @Column(name = "api_method")
    private APIMethod apiMethod;
    @Column(name = "request_body_template")
    private String requestBodyTemplate;
    @Column(name = "respond_template")
    private String respondTemplate;
    //TODO
//    @Column(name = "")
//    private String


    public enum APIMethod{
        GET,POST,PUT,PATCH,DELETE;
    }
}
