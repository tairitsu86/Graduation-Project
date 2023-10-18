package gradutionProject.IMIGSystem.lineService.rabbitMQ;

import gradutionProject.IMIGSystem.lineService.dto.MessageEventDto;

public interface MQEventPublisher {
    void publishEvent(MessageEventDto event);

}
