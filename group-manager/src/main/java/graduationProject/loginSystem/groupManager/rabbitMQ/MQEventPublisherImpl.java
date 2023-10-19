package graduationProject.loginSystem.groupManager.rabbitMQ;

import graduationProject.loginSystem.groupManager.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class MQEventPublisherImpl implements MQEventPublisher{
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishNotificationEvent(NotificationDto notificationDto) {
        rabbitTemplate.convertAndSend(RabbitmqConfig.SYS_NTF_EXCHANGE,"",notificationDto);
    }
}
