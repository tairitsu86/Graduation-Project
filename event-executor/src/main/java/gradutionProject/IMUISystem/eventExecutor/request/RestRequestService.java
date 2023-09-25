package gradutionProject.IMUISystem.eventExecutor.request;

import gradutionProject.IMUISystem.eventExecutor.entity.CommConfig;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface RestRequestService {
    ResponseEntity sendEventRequest(CommConfig commConfig, Map<String,String> variables);
}
