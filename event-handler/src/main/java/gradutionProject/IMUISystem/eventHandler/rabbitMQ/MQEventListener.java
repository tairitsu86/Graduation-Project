package gradutionProject.IMUISystem.eventHandler.rabbitMQ;

import gradutionProject.IMUISystem.eventHandler.dto.MessageEventDto;
import gradutionProject.IMUISystem.eventHandler.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static gradutionProject.IMUISystem.eventHandler.rabbitMQ.RabbitmqConfig.userEventQueue;

@Slf4j
@RequiredArgsConstructor
@Component
public class MQEventListener {
    private final EventHandler eventHandler;
    @RabbitListener(queues={userEventQueue})
    public void receive(MessageEventDto messageEventDto) {
        try{
            log.info("Message event: {}",messageEventDto);
            eventHandler.newMessage(messageEventDto.getImUserData(),messageEventDto.getMessage());
        }catch (Exception e){
            log.info("Something wrong with: {}",e);
        }
    }
}
