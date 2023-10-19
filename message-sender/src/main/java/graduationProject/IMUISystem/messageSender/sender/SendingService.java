package graduationProject.IMUISystem.messageSender.sender;

import graduationProject.IMUISystem.messageSender.dto.SendingEventDto;

public interface SendingService {
    void sendingEvent(SendingEventDto sendingEventDto);
}
