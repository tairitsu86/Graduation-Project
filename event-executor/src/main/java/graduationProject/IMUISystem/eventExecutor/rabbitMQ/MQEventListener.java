package graduationProject.IMUISystem.eventExecutor.rabbitMQ;

import com.fasterxml.jackson.databind.ObjectMapper;
import graduationProject.IMUISystem.eventExecutor.communication.CommunicationService;
import graduationProject.IMUISystem.eventExecutor.dto.ExecuteEventDto;
import graduationProject.IMUISystem.eventExecutor.repository.RepositoryService;
import graduationProject.IMUISystem.eventExecutor.respond.RespondService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class MQEventListener {
    private final RepositoryService repositoryService;
    private final CommunicationService communicationService;
    private final RespondService respondService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues={RabbitmqConfig.EXECUTE_EVENT_QUEUE})
    public void receive(ExecuteEventDto executeEventDto) {
        log.info("Execute event: {}", executeEventDto);
        try {
            communicationService.handleExecuteEvent(executeEventDto);
        }catch (Exception e){
            log.info("Something wrong with: {}",e.getMessage(),e);
        }
    }

    @RabbitListener(queues={RabbitmqConfig.NOTIFY_USER_QUEUE})
    public void receive(Map<String,Object> notifyEvent) {
        log.info("Notify event: {}", notifyEvent);
        try {
            String eventName = String.format("%s_%s",notifyEvent.get("from"),notifyEvent.get("notificationType"));
            String executor = (String) notifyEvent.get("executor");
            String jsonData = objectMapper.writeValueAsString(notifyEvent);
            if(!repositoryService.isRespondConfigExist(eventName)) return;
            respondService.respond(executor,repositoryService.getRespondConfig(eventName), null,jsonData);
        }catch (Exception e){
            log.info("Something wrong with: {}",e.getMessage(),e);
        }
    }


}
