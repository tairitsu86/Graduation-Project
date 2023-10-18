package gradutionProject.IMUISystem.messageSender.sender;

import gradutionProject.IMUISystem.messageSender.dto.SendingEventDto;

public interface SendingService {
    void sendingEvent(SendingEventDto sendingEventDto);
}
