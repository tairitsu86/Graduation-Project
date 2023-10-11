package gradutionProject.IMUISystem.eventHandler.rabbitMQ;

import gradutionProject.IMUISystem.eventHandler.dto.ExecuteEventDto;
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
        rabbitTemplate.convertAndSend(IMUI_IBC_EXCHANGE, SEND_MESSAGE_QUEUE, sendingEventDto);
    }

    @Override
    public void publishExecuteEvent(ExecuteEventDto executeEventDto) {
        rabbitTemplate.convertAndSend(IMUI_RAE_EXCHANGE, EXECUTE_EVENT_QUEUE, executeEventDto);
    }
}
