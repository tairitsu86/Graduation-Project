package gradutionProject.IMUISystem.eventExecutor.rabbitMQ;

import gradutionProject.IMUISystem.eventExecutor.dto.ExecuteEventDto;
import gradutionProject.IMUISystem.eventExecutor.repository.APIDataRepository;
import gradutionProject.IMUISystem.eventExecutor.repository.RepositoryService;
import gradutionProject.IMUISystem.eventExecutor.request.RestRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static gradutionProject.IMUISystem.eventExecutor.rabbitMQ.RabbitmqConfig.executeEventQueue;

@Slf4j
@RequiredArgsConstructor
@Component
public class MQEventListener {
    private final RepositoryService repositoryService;
    private final RestRequestService restRequestService;

    @RabbitListener(queues={executeEventQueue})
    public void receive(ExecuteEventDto executeEventDto) {
        log.info("Execute event: {}", executeEventDto);
        try {
            switch (repositoryService.getEventType(executeEventDto.getEventName())){
                case MQ -> {}
                case API -> restRequestService.sendEventRequest(repositoryService.getApiDataByEventName(executeEventDto.getEventName()),executeEventDto.getVariables());
                default -> {return;}
            }
        }catch (NullPointerException e){
            log.info("Some field be null!");
        }
    }
}
