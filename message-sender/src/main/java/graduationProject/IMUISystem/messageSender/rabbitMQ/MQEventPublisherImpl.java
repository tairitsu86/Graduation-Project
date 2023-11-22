package graduationProject.IMUISystem.messageSender.rabbitMQ;

import graduationProject.IMUISystem.messageSender.dto.SendingServiceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static graduationProject.IMUISystem.messageSender.rabbitMQ.RabbitmqConfig.IMIG_IPS_EXCHANGE;


@Service
@Slf4j
@RequiredArgsConstructor
public class MQEventPublisherImpl implements MQEventPublisher{
    private final RabbitTemplate rabbitTemplate;
    @Override
    public void publishSendingServiceEvent(String platform, SendingServiceDto sendingServiceDto) {
        rabbitTemplate.convertAndSend(IMIG_IPS_EXCHANGE, String.format("IM-Integration-System.%s", platform), sendingServiceDto);
        log.info("Send to exchange [{}] with routingKey [{}], event [{}]", IMIG_IPS_EXCHANGE, String.format("IM-Integration-System.%s", platform), sendingServiceDto);
    }
}
