package gradutionProject.IMUISystem.eventExecutor.request;

import gradutionProject.IMUISystem.eventExecutor.dto.CommConfigDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface RestRequestService {
    ResponseEntity<String> sendEventRequest(CommConfigDto commConfigDto);

    List<String> getGroupUsers(List<String> groups);
}
