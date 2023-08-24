package gradutionProject.IMUISystem.webhookHandler.rabbitMQ;

import gradutionProject.IMUISystem.webhookHandler.dto.MessageEventDto;

public interface MQEventPublisher {
    void publishEvent(MessageEventDto event);

}
