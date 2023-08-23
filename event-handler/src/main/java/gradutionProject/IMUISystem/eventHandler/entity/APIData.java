package gradutionProject.IMUISystem.eventHandler.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class APIData{
    private String urlTemplate;
    @Enumerated(EnumType.STRING)
    private APIMethod apiMethod;
    private String requestBodyTemplate;

    public enum APIMethod{
        GET,POST,PUT,PATCH,DELETE;
    }
}
