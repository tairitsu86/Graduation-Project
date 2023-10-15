package gradutionProject.IMUISystem.eventExecutor.rabbitMQ;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gradutionProject.IMUISystem.eventExecutor.dto.CommConfigDto;
import gradutionProject.IMUISystem.eventExecutor.dto.NewCustomizeEventDto;
import gradutionProject.IMUISystem.eventExecutor.dto.SendingEventDto;
import gradutionProject.IMUISystem.eventExecutor.entity.MenuOption;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static gradutionProject.IMUISystem.eventExecutor.rabbitMQ.RabbitmqConfig.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MQEventPublisherImpl implements MQEventPublisher{
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void publishSendingEvent(SendingEventDto sendingEventDto) {
        rabbitTemplate.convertAndSend(IMUI_IBC_EXCHANGE, SEND_MESSAGE_QUEUE,sendingEventDto);
    }

    @Override
    public void notifyUser(List<String> users, String message) {
        publishSendingEvent(
                SendingEventDto.builder()
                        .usernameList(users)
                        .message(message)
                        .build()
        );
    }

    @Override
    public void publishCustomEvent(CommConfigDto commConfigDto) {
        try {
            Map<String, Object> jsonMap = objectMapper.readValue(commConfigDto.getBody(), new TypeReference<Map<String, Object>>() {});
            rabbitTemplate.convertAndSend(SYS_SVC_EXCHANGE, commConfigDto.getUrl(), jsonMap);
        } catch (Exception e) {
            log.info("publishCustomEvent convert json to Map<String,Object> got error: {}",e.getMessage(),e);
        }
    }

    @Override
    public void publishMenuEvent(NewCustomizeEventDto newCustomizeEventDto) {
        rabbitTemplate.convertAndSend(IMUI_RAE_EXCHANGE, NEW_EVENT_QUEUE, newCustomizeEventDto);
    }

    @Override
    public void newMenu(String description, List<MenuOption> options, Map<String,String> parameters) {
        publishMenuEvent(
                NewCustomizeEventDto.builder()
                        .eventName("MENU")
                        .description(description)
                        .options(options)
                        .parameters(parameters)
                        .build()
        );
    }

}
