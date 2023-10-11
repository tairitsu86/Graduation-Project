package gradutionProject.IMUISystem.eventHandler.rabbitMQ;

import gradutionProject.IMUISystem.eventHandler.dto.ExecuteEventDto;
import gradutionProject.IMUISystem.eventHandler.dto.SendingEventDto;

public interface MQEventPublisher {
    void publishSendingEvent(SendingEventDto sendingEventDto);

    void publishExecuteEvent(ExecuteEventDto executeEventDto);
}
