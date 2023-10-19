package graduationProject.IMUISystem.eventHandler.rabbitMQ;

import graduationProject.IMUISystem.eventHandler.dto.ExecuteEventDto;
import graduationProject.IMUISystem.eventHandler.dto.SendingEventDto;

public interface MQEventPublisher {
    void publishSendingEvent(SendingEventDto sendingEventDto);

    void publishExecuteEvent(ExecuteEventDto executeEventDto);
}
