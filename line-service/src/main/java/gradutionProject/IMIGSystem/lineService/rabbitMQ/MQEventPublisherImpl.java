package gradutionProject.IMIGSystem.lineService.rabbitMQ;

import gradutionProject.IMIGSystem.lineService.dto.MessageEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static gradutionProject.IMIGSystem.lineService.rabbitMQ.RabbitmqConfig.*;


@Service
@RequiredArgsConstructor
public class MQEventPublisherImpl implements MQEventPublisher{
    private final RabbitTemplate rabbitTemplate;
    @Override
    public void publishEvent(MessageEventDto event) {
        rabbitTemplate.convertAndSend(IMUI_IBC_EXCHANGE, IM_USER_MESSAGE_QUEUE, event);
    }
}
