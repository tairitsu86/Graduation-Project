package gradutionProject.IMUISystem.eventExecutor.rabbitMQ;

import gradutionProject.IMUISystem.eventExecutor.dto.CommConfigDto;
import gradutionProject.IMUISystem.eventExecutor.dto.ExecuteEventDto;
import gradutionProject.IMUISystem.eventExecutor.entity.CommConfig;
import gradutionProject.IMUISystem.eventExecutor.repository.RepositoryService;
import gradutionProject.IMUISystem.eventExecutor.request.RestRequestService;
import gradutionProject.IMUISystem.eventExecutor.respond.RespondService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

import java.util.Map;

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

    @RabbitListener(queues={EXECUTE_EVENT_QUEUE})
    public void receive(ExecuteEventDto executeEventDto) {
        log.info("Execute event: {}", executeEventDto);
        try {
            if(!repositoryService.isCommConfigExist(executeEventDto.getEventName())) return;
            CommConfig commConfig = repositoryService.getCommConfig(executeEventDto.getEventName());
            switch (commConfig.getMethodType()){
                case MQ -> mqEventPublisher.publishCustomEvent(CommConfigDto.createCommConfigDto(commConfig,executeEventDto.getVariables()));
                default -> {
                    ResponseEntity<String> response =
                            restRequestService.sendEventRequest(CommConfigDto.createCommConfigDto(commConfig,executeEventDto.getVariables()));

                    if(!repositoryService.isRespondConfigExist(executeEventDto.getEventName())) return;
                    respondService.respond(executeEventDto.getExecutor(),repositoryService.getRespondConfig(executeEventDto.getEventName()),response.getBody());
                }
            }

        }catch (Exception e){
            log.info("Something wrong with: {}",e);
        }
    }
    @RabbitListener(queues={NOTIFY_USER_QUEUE})
    public void receive(Map<String,Object> notifyEvent) {
        log.info("Execute event: {}", notifyEvent);
        try {
            notifyEvent.get("");
        }catch (Exception e){
            log.info("Something wrong with: {}",e);
        }
    }

}
