package graduationProject.IMUISystem.eventHandler.rabbitMQ;

import graduationProject.IMUISystem.eventHandler.dto.MessageEventDto;
import graduationProject.IMUISystem.eventHandler.dto.NewCustomizeEventDto;
import graduationProject.IMUISystem.eventHandler.entity.IMUserData;
import graduationProject.IMUISystem.eventHandler.handler.EventHandler;
import graduationProject.IMUISystem.eventHandler.repository.RepositoryService;
import graduationProject.IMUISystem.eventHandler.request.RestRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class MQEventListener {
    private final EventHandler eventHandler;
    private final RepositoryService repositoryService;
    private final RestRequestService restRequestService;
    @RabbitListener(queues={RabbitmqConfig.IM_USER_MESSAGE_QUEUE})
    public void receive(MessageEventDto messageEventDto) {
        try{
            log.info("Message event: {}",messageEventDto);
            eventHandler.newMessage(messageEventDto.getImUserData(),messageEventDto.getMessage());
        }catch (Exception e){
            log.info("Something wrong with: {}",e.getMessage(),e);
        }
    }

    @RabbitListener(queues={RabbitmqConfig.NEW_EVENT_QUEUE})
    public void receive(NewCustomizeEventDto newCustomizeEventDto) {
        try{
            log.info("Message event: {}",newCustomizeEventDto);
            IMUserData imUserData = restRequestService.getIMUserData(newCustomizeEventDto.getUsername());
            if(imUserData == null){
                log.info("User didn't exist or didn't login!");
                return;
            }
            if (newCustomizeEventDto.getEventName().equals("MENU")) {
                eventHandler.menuEvent(
                        imUserData,
                        newCustomizeEventDto.getUsername(),
                        newCustomizeEventDto.getDescription(),
                        newCustomizeEventDto.getMenuOptions(),
                        newCustomizeEventDto.getParameters()
                );
                return;
            }

            if (!repositoryService.checkEventName(newCustomizeEventDto.getEventName())) return;

            eventHandler.newUserEvent(
                    imUserData,
                    newCustomizeEventDto.getUsername(),
                    newCustomizeEventDto.getEventName(),
                    newCustomizeEventDto.getParameters()
            );

        }catch (Exception e){
            log.info("Something wrong with: {}",e.getMessage(),e);
        }
    }
}
