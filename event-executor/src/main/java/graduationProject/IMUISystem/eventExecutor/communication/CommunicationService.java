package graduationProject.IMUISystem.eventExecutor.communication;

import graduationProject.IMUISystem.eventExecutor.dto.ExecuteEventDto;

public interface CommunicationService {
    void handleExecuteEvent(ExecuteEventDto executeEventDto);
}
