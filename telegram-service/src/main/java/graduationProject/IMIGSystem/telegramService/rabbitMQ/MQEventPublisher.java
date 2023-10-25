package graduationProject.IMIGSystem.telegramService.rabbitMQ;

import graduationProject.IMIGSystem.telegramService.dto.MessageEventDto;

public interface MQEventPublisher {
    void publishEvent(MessageEventDto event);

}
