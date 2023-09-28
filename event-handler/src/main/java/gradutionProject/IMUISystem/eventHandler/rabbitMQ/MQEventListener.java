package gradutionProject.IMUISystem.eventHandler.rabbitMQ;

import gradutionProject.IMUISystem.eventHandler.dto.MessageEventDto;
import gradutionProject.IMUISystem.eventHandler.dto.NewCustomizeEventDto;
import gradutionProject.IMUISystem.eventHandler.entity.IMUserData;
import gradutionProject.IMUISystem.eventHandler.handler.EventHandler;
import gradutionProject.IMUISystem.eventHandler.repository.RepositoryService;
import gradutionProject.IMUISystem.eventHandler.request.RestRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static gradutionProject.IMUISystem.eventHandler.rabbitMQ.RabbitmqConfig.customizeEventQueue;
import static gradutionProject.IMUISystem.eventHandler.rabbitMQ.RabbitmqConfig.userEventQueue;

@Slf4j
@RequiredArgsConstructor
@Component
public class MQEventListener {
    private final EventHandler eventHandler;
    private final RepositoryService repositoryService;
    private final RestRequestService restRequestService;
    @RabbitListener(queues={userEventQueue})
    public void receive(MessageEventDto messageEventDto) {
        try{
            log.info("Message event: {}",messageEventDto);
            eventHandler.newMessage(messageEventDto.getImUserData(),messageEventDto.getMessage());
        }catch (Exception e){
            log.info("Something wrong with: {}",e);
        }
    }

    @RabbitListener(queues={customizeEventQueue})
    public void receive(NewCustomizeEventDto newCustomizeEventDto) {
        try{
            log.info("Message event: {}",newCustomizeEventDto);
            IMUserData imUserData = restRequestService.getIMUserData(newCustomizeEventDto.getUsername());
            if(imUserData == null){
                log.info("User didn't exist or didn't login!");
                return;
            }
            switch (newCustomizeEventDto.getEventName()){
                case "MENU"-> eventHandler.menuEvent(
                        imUserData,
                        newCustomizeEventDto.getUsername(),
                        newCustomizeEventDto.getDescription(),
                        newCustomizeEventDto.getEvents(),
                        newCustomizeEventDto.getParameters()
                );
                default -> {
                    if(!repositoryService.checkEventName(newCustomizeEventDto.getEventName())) return;
                    eventHandler.newUserEvent(
                            imUserData,
                            newCustomizeEventDto.getUsername(),
                            newCustomizeEventDto.getEventName(),
                            newCustomizeEventDto.getParameters()
                    );
                }
            }
        }catch (Exception e){
            log.info("Something wrong with: {}",e);
        }
    }
}
