package gradutionProject.IMUISystem.eventExecutor.rabbitMQ;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static gradutionProject.IMUISystem.eventExecutor.rabbitMQ.RabbitmqConfig.topicExchange;

@Service
@RequiredArgsConstructor
public class MQEventPublisherImpl implements MQEventPublisher{
    private final RabbitTemplate rabbitTemplate;

}
