package gradutionProject.IMUISystem.eventExecutor.rabbitMQ;

import com.fasterxml.jackson.databind.ObjectMapper;
import gradutionProject.IMUISystem.eventExecutor.dto.CommConfigDto;
import gradutionProject.IMUISystem.eventExecutor.dto.ExecuteEventDto;
import gradutionProject.IMUISystem.eventExecutor.entity.CommConfig;
import gradutionProject.IMUISystem.eventExecutor.entity.MethodType;
import gradutionProject.IMUISystem.eventExecutor.repository.RepositoryService;
import gradutionProject.IMUISystem.eventExecutor.request.RestRequestService;
import gradutionProject.IMUISystem.eventExecutor.respond.RespondService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

import static gradutionProject.IMUISystem.eventExecutor.rabbitMQ.RabbitmqConfig.EXECUTE_EVENT_QUEUE;
import static gradutionProject.IMUISystem.eventExecutor.rabbitMQ.RabbitmqConfig.NOTIFY_USER_QUEUE;

@Slf4j
@RequiredArgsConstructor
@Component
public class MQEventListener {
    private final RepositoryService repositoryService;
    private final RestRequestService restRequestService;
    private final MQEventPublisher mqEventPublisher;
    private final RespondService respondService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues={EXECUTE_EVENT_QUEUE})
    public void receive(ExecuteEventDto executeEventDto) {
        log.info("Execute event: {}", executeEventDto);
        try {
            if(!repositoryService.isCommConfigExist(executeEventDto.getEventName())) return;
            CommConfig commConfig = repositoryService.getCommConfig(executeEventDto.getEventName());
            if (Objects.requireNonNull(commConfig.getMethodType()) == MethodType.MQ) {
                mqEventPublisher.publishCustomEvent(CommConfigDto.createCommConfigDto(commConfig, executeEventDto.getVariables()));
            } else {
                ResponseEntity<String> response =
                        restRequestService.sendEventRequest(CommConfigDto.createCommConfigDto(commConfig, executeEventDto.getVariables()));

                if (!repositoryService.isRespondConfigExist(executeEventDto.getEventName())) return;
                respondService.respond(executeEventDto.getExecutor(), repositoryService.getRespondConfig(executeEventDto.getEventName()), response.getBody());
            }

        }catch (Exception e){
            log.info("Something wrong with: {}",e.getMessage(),e);
        }
    }
    @RabbitListener(queues={NOTIFY_USER_QUEUE})
    public void receive(Map<String,Object> notifyEvent) {
        log.info("Execute event: {}", notifyEvent);
        try {
            String eventName = String.format("%s-%s",notifyEvent.get("from"),notifyEvent.get("notificationType"));
            String executor = (String) notifyEvent.get("executor");
            String jsonData = objectMapper.writeValueAsString(notifyEvent);
            if(!repositoryService.isRespondConfigExist(eventName)) return;
            respondService.respond(executor,repositoryService.getRespondConfig(eventName),jsonData);
        }catch (Exception e){
            log.info("Something wrong with: {}",e.getMessage(),e);
        }
    }

}
