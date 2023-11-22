package graduationProject.IMIGSystem.lineService.rabbitMQ;

import graduationProject.IMIGSystem.lineService.dto.MessageEventDto;

public interface MQEventPublisher {
    void publishEvent(MessageEventDto event);

}
