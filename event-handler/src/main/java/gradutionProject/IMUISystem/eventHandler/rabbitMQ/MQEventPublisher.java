package gradutionProject.IMUISystem.eventHandler.rabbitMQ;

import gradutionProject.IMUISystem.eventHandler.dto.LoginEventDto;
import gradutionProject.IMUISystem.eventHandler.dto.SendingEventDto;

public interface MQEventPublisher {
    void publishSendingEvent(SendingEventDto sendingEventDto);
}
