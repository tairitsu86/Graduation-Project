package gradutionProject.IMUISystem.eventExecutor.rabbitMQ;

import gradutionProject.IMUISystem.eventExecutor.dto.ExecuteEventDto;
import gradutionProject.IMUISystem.eventExecutor.entity.CommConfig;
import gradutionProject.IMUISystem.eventExecutor.repository.RepositoryService;
import gradutionProject.IMUISystem.eventExecutor.request.RestRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static gradutionProject.IMUISystem.eventExecutor.rabbitMQ.RabbitmqConfig.executeEventQueue;

@Slf4j
@RequiredArgsConstructor
@Component
public class MQEventListener {
    private final RepositoryService repositoryService;
    private final RestRequestService restRequestService;
    private final MQEventPublisher mqEventPublisher;

    @RabbitListener(queues={executeEventQueue})
    public void receive(ExecuteEventDto executeEventDto) {
        log.info("Execute event: {}", executeEventDto);
        try {
            if(!repositoryService.isCommConfigExist(executeEventDto.getEventName())) return;
            CommConfig commConfig = repositoryService.getCommConfig(executeEventDto.getEventName());
            switch (commConfig.getMethodType()){
                case MQ -> {}
                default -> {
                    ResponseEntity<String> response =
                            restRequestService.sendEventRequest(
                                    repositoryService.getCommConfig(
                                            executeEventDto.getEventName()
                                    ),
                                    executeEventDto.getVariables()
                            );

                    if(!repositoryService.isNotifyConfigExist(executeEventDto.getEventName())) return;

                    mqEventPublisher.notifyUser(
                            new ArrayList<>(){{add(executeEventDto.getExecutor());}},
                            repositoryService.getNotifyConfig(executeEventDto.getEventName()),
                            response.getBody());
                }
            }

        }catch (Exception e){
            log.info("Something wrong with: {}!",e);
        }
    }
}
