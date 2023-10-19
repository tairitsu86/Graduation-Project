package graduationProject.IMUISystem.eventExecutor.request;

import graduationProject.IMUISystem.eventExecutor.dto.CommConfigDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestRequestService {
    ResponseEntity<String> sendEventRequest(CommConfigDto commConfigDto);

    List<String> getGroupUsers(List<String> groups);
}
