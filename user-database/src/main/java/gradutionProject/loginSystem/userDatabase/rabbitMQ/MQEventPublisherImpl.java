package gradutionProject.loginSystem.userDatabase.rabbitMQ;

import gradutionProject.loginSystem.userDatabase.dto.LoginEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static gradutionProject.loginSystem.userDatabase.rabbitMQ.RabbitmqConfig.loginEventQueue;
import static gradutionProject.loginSystem.userDatabase.rabbitMQ.RabbitmqConfig.topicExchange;


@Service
@RequiredArgsConstructor
public class MQEventPublisherImpl implements MQEventPublisher{
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishLoginEvent(LoginEventDto loginEventDto) {
        rabbitTemplate.convertAndSend(topicExchange,loginEventQueue,loginEventDto);
    }
}
