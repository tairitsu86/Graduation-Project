package gradutionProject.IMUISystem.eventHandler.rabbitMQ;

import gradutionProject.IMUISystem.eventHandler.dto.LoginEventDto;
import gradutionProject.IMUISystem.eventHandler.dto.SendingEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static gradutionProject.IMUISystem.eventHandler.rabbitMQ.RabbitmqConfig.*;

@Service
@RequiredArgsConstructor
public class MQEventPublisherImpl implements MQEventPublisher{
    private final RabbitTemplate rabbitTemplate;
    @Override
    public void publishSendingEvent(SendingEventDto sendingEventDto) {
        rabbitTemplate.convertAndSend(topicExchangeName, sendingEventQueue,sendingEventDto);
    }

    @Override
    public void publishLoginEvent(LoginEventDto loginEventDto) {
        rabbitTemplate.convertAndSend(topicExchangeName, loginEventQueue,loginEventDto);
    }
}
