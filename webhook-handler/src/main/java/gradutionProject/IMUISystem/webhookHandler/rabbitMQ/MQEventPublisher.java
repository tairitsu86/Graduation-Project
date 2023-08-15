package gradutionProject.IMUISystem.webhookHandler.rabbitMQ;

import gradutionProject.IMUISystem.webhookHandler.dto.Event;

public interface MQEventPublisher {
    void publishEvent(Event event);

}
