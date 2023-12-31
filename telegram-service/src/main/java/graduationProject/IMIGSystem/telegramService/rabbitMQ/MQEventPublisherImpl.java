package graduationProject.IMIGSystem.telegramService.rabbitMQ;


import graduationProject.IMIGSystem.telegramService.dto.MessageEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static graduationProject.IMIGSystem.telegramService.rabbitMQ.RabbitmqConfig.*;


@Service
@RequiredArgsConstructor
public class MQEventPublisherImpl implements MQEventPublisher{
    private final RabbitTemplate rabbitTemplate;
    @Override
    public void publishEvent(MessageEventDto event) {
        rabbitTemplate.convertAndSend(IMUI_IBC_EXCHANGE, IM_USER_MESSAGE_QUEUE, event);
    }
}
