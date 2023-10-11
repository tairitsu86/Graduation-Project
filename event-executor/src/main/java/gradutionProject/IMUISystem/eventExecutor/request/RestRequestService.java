package gradutionProject.IMUISystem.eventExecutor.request;

import gradutionProject.IMUISystem.eventExecutor.dto.CommConfigDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface RestRequestService {
    ResponseEntity sendEventRequest(CommConfigDto commConfigDto);
}
