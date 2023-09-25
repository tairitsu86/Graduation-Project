package gradutionProject.IMUISystem.eventExecutor.request;

import gradutionProject.IMUISystem.eventExecutor.entity.APIData;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface RestRequestService {
    ResponseEntity sendEventRequest(APIData apiData, Map<String,String> variables);
}
