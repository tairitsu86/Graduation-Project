package gradutionProject.loginSystem.userDatabase.rabbitMQ;

import gradutionProject.loginSystem.userDatabase.dto.LoginEventDto;
import gradutionProject.loginSystem.userDatabase.dto.LogoutEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static gradutionProject.loginSystem.userDatabase.rabbitMQ.RabbitmqConfig.*;


@Service
@RequiredArgsConstructor
public class MQEventPublisherImpl implements MQEventPublisher{
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishLoginEvent(LoginEventDto loginEventDto) {
        rabbitTemplate.convertAndSend(topicExchange,loginEventQueue,loginEventDto);
    }

    @Override
    public void publishLogoutEvent(LogoutEventDto logoutEventDto) {
        rabbitTemplate.convertAndSend(topicExchange,logoutEventQueue,logoutEventDto);
    }
}
