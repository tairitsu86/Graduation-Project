package graduationProject.IMUISystem.messageSender.rabbitMQ;

import graduationProject.IMUISystem.messageSender.sender.SendingService;
import graduationProject.IMUISystem.messageSender.dto.SendingEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static graduationProject.IMUISystem.messageSender.rabbitMQ.RabbitmqConfig.SEND_MESSAGE_QUEUE;

@Slf4j
@RequiredArgsConstructor
@Component
public class MQEventListener {
    private final SendingService sendingService;


    @RabbitListener(queues={SEND_MESSAGE_QUEUE})
    public void receive(SendingEventDto sendingEventDto) {
        try{
            log.info("Sending Event:"+sendingEventDto.toString());
            sendingService.sendingEvent(sendingEventDto);
        }catch (Exception e){
            log.info("Something go wrong: {}",e.getMessage(),e);
        }

    }
}
