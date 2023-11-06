package graduationProject.IMIGSystem.testConsole.rabbitMQ;

import graduationProject.IMIGSystem.testConsole.dto.MessageEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static graduationProject.IMIGSystem.testConsole.rabbitMQ.RabbitmqConfig.IMUI_IBC_EXCHANGE;
import static graduationProject.IMIGSystem.testConsole.rabbitMQ.RabbitmqConfig.IM_USER_MESSAGE_QUEUE;


@Service
@RequiredArgsConstructor
public class MQEventPublisherImpl implements MQEventPublisher{
    private final RabbitTemplate rabbitTemplate;
    @Override
    public void publishEvent(MessageEventDto event) {
        rabbitTemplate.convertAndSend(IMUI_IBC_EXCHANGE, IM_USER_MESSAGE_QUEUE, event);
    }
}
