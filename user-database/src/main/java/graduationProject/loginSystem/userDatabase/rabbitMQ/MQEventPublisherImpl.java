package graduationProject.loginSystem.userDatabase.rabbitMQ;

import graduationProject.loginSystem.userDatabase.dto.LoginEventDto;
import graduationProject.loginSystem.userDatabase.dto.LogoutEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static graduationProject.loginSystem.userDatabase.rabbitMQ.RabbitmqConfig.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class MQEventPublisherImpl implements MQEventPublisher{
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishLoginEvent(LoginEventDto loginEventDto) {
        rabbitTemplate.convertAndSend(LOGIN_UA_EXCHANGE, LOGIN_LOG_QUEUE, loginEventDto);
        log.info("Send to exchange [{}] with routingKey [{}], event [{}]", LOGIN_UA_EXCHANGE, LOGIN_LOG_QUEUE, loginEventDto);
    }

    @Override
    public void publishLogoutEvent(LogoutEventDto logoutEventDto) {
        rabbitTemplate.convertAndSend(LOGIN_UA_EXCHANGE, LOGOUT_LOG_QUEUE, logoutEventDto);
        log.info("Send to exchange [{}] with routingKey [{}], event [{}]", LOGIN_UA_EXCHANGE, LOGOUT_LOG_QUEUE, logoutEventDto);
    }
}
