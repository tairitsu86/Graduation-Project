package gradutionProject.IMUISystem.webhookHandler.rabbitMQ;

import gradutionProject.IMUISystem.webhookHandler.dto.MessageEvent;

public interface MQEventPublisher {
    void publishEvent(MessageEvent event);

}
