package gradutionProject.IMUISystem.webhookHandler.rabbitMQ;

import gradutionProject.IMUISystem.webhookHandler.dto.MessageEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MQEventPublisherImpl implements MQEventPublisher{
    private final RabbitTemplate rabbitTemplate;
    @Override
    public void publishEvent(MessageEventDto event) {
        rabbitTemplate.convertAndSend(RabbitmqConfig.topicExchangeName, "im-ui.webhook-handler", event);
    }
}
