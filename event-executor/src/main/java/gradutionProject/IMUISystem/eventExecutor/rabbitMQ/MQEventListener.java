package gradutionProject.IMUISystem.eventExecutor.rabbitMQ;

import gradutionProject.IMUISystem.eventExecutor.dto.ExecuteEventDto;
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
            switch (repositoryService.getEventType(executeEventDto.getEventName())){
                case MQ -> {}
                case API -> {
                    ResponseEntity<String> response =
                            restRequestService.sendEventRequest(
                                    repositoryService.getApiDataByEventName(
                                            executeEventDto.getEventName()
                                    ),
                                    executeEventDto.getVariables()
                            );
                    mqEventPublisher.notifyUser(
                            new ArrayList<String>(){{executeEventDto.getExecutor();}},
                            repositoryService.getNotifyData(executeEventDto.getEventName()),
                            response.getBody());
                }
                default -> {return;}
            }

        }catch (NullPointerException e){
            log.info("Some field be null!");
        }
    }
}
