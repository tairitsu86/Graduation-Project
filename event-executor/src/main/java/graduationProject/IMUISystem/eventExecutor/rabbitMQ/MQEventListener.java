package graduationProject.IMUISystem.eventExecutor.rabbitMQ;

import com.fasterxml.jackson.databind.ObjectMapper;
import graduationProject.IMUISystem.eventExecutor.dto.CommConfigDto;
import graduationProject.IMUISystem.eventExecutor.dto.ExecuteEventDto;
import graduationProject.IMUISystem.eventExecutor.entity.CommConfig;
import graduationProject.IMUISystem.eventExecutor.entity.MethodType;
import graduationProject.IMUISystem.eventExecutor.repository.RepositoryService;
import graduationProject.IMUISystem.eventExecutor.request.RestRequestService;
import graduationProject.IMUISystem.eventExecutor.respond.RespondService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class MQEventListener {
    private final RepositoryService repositoryService;
    private final RestRequestService restRequestService;
    private final MQEventPublisher mqEventPublisher;
    private final RespondService respondService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues={RabbitmqConfig.EXECUTE_EVENT_QUEUE})
    public void receive(ExecuteEventDto executeEventDto) {
        log.info("Execute event: {}", executeEventDto);
        try {
            if(!repositoryService.isCommConfigExist(executeEventDto.getEventName())) return;
            CommConfig commConfig = repositoryService.getCommConfig(executeEventDto.getEventName());
            if (Objects.requireNonNull(commConfig.getMethodType()) == MethodType.MQ) {
                mqEventPublisher.publishCustomEvent(CommConfigDto.createCommConfigDto(commConfig, executeEventDto.getParameters()));
            } else {
                ResponseEntity<String> response =
                        restRequestService.sendEventRequest(CommConfigDto.createCommConfigDto(commConfig, executeEventDto.getParameters()));

                if (!repositoryService.isRespondConfigExist(executeEventDto.getEventName())) return;
                respondService.respond(executeEventDto.getExecutor(), repositoryService.getRespondConfig(executeEventDto.getEventName()), executeEventDto.getParameters(),response.getBody());
            }

        }catch (Exception e){
            log.info("Something wrong with: {}",e.getMessage(),e);
        }
    }
    @RabbitListener(queues={RabbitmqConfig.NOTIFY_USER_QUEUE})
    public void receive(Map<String,Object> notifyEvent) {
        log.info("Execute event: {}", notifyEvent);
        try {
            String eventName = String.format("%s-%s",notifyEvent.get("from"),notifyEvent.get("notificationType"));
            String executor = (String) notifyEvent.get("executor");
            String jsonData = objectMapper.writeValueAsString(notifyEvent);
            if(!repositoryService.isRespondConfigExist(eventName)) return;
            respondService.respond(executor,repositoryService.getRespondConfig(eventName), null,jsonData);
        }catch (Exception e){
            log.info("Something wrong with: {}",e.getMessage(),e);
        }
    }

}
