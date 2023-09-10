package gradutionProject.IMUISystem.eventHandler.rabbitMQ;

import gradutionProject.IMUISystem.eventHandler.dto.MessageEventDto;
import gradutionProject.IMUISystem.eventHandler.handler.MessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static gradutionProject.IMUISystem.eventHandler.rabbitMQ.RabbitmqConfig.userEventQueue;

@Slf4j
@RequiredArgsConstructor
@Component
public class MQEventListener {
    private final MessageHandler messageHandler;
    @RabbitListener(queues={userEventQueue})
    public void receive(MessageEventDto messageEventDto) {
        if(messageEventDto==null||
                messageEventDto.getImUserData()==null||
                messageEventDto.getImUserData().getPlatform()==null||
                messageEventDto.getImUserData().getUserId()==null||
                messageEventDto.getMessage()==null
        )return;
        log.info("Message event: "+messageEventDto.toString());
        messageHandler.checkUser(messageEventDto.getImUserData(),messageEventDto.getMessage());
    }
}
