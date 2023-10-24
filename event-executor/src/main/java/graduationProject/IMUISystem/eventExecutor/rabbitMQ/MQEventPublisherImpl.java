package graduationProject.IMUISystem.eventExecutor.rabbitMQ;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import graduationProject.IMUISystem.eventExecutor.dto.ExecuteEventDto;
import graduationProject.IMUISystem.eventExecutor.dto.NewCustomizeEventDto;
import graduationProject.IMUISystem.eventExecutor.entity.MenuOption;
import graduationProject.IMUISystem.eventExecutor.dto.CommConfigDto;
import graduationProject.IMUISystem.eventExecutor.dto.SendingEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static graduationProject.IMUISystem.eventExecutor.rabbitMQ.RabbitmqConfig.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MQEventPublisherImpl implements MQEventPublisher{
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void publishSendingEvent(SendingEventDto sendingEventDto) {
        rabbitTemplate.convertAndSend(IMUI_IBC_EXCHANGE, SEND_MESSAGE_QUEUE, sendingEventDto);
        log.info("Send to exchange [{}] with routingKey [{}], event [{}]", IMUI_IBC_EXCHANGE, SEND_MESSAGE_QUEUE, sendingEventDto);
    }

    @Override
    public void publishExecuteEvent(ExecuteEventDto executeEventDto) {
        rabbitTemplate.convertAndSend(IMUI_RAE_EXCHANGE, EXECUTE_EVENT_QUEUE, executeEventDto);
        log.info("Send to exchange [{}] with routingKey [{}], event [{}]", IMUI_RAE_EXCHANGE, EXECUTE_EVENT_QUEUE, executeEventDto);
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
            log.info("Send to exchange [{}] with routingKey [{}], event [{}]", SYS_SVC_EXCHANGE, commConfigDto.getUrl(), jsonMap);
        } catch (Exception e) {
            log.info("publishCustomEvent convert json to Map<String,Object> got error: {}",e.getMessage(),e);
        }
    }

    @Override
    public void publishMenuEvent(NewCustomizeEventDto newCustomizeEventDto) {
        rabbitTemplate.convertAndSend(IMUI_RAE_EXCHANGE, NEW_EVENT_QUEUE, newCustomizeEventDto);
        log.info("Send to exchange [{}] with routingKey [{}], event [{}]", IMUI_RAE_EXCHANGE, NEW_EVENT_QUEUE, newCustomizeEventDto);
    }

    @Override
    public void newMenu(String username, String description, List<MenuOption> options, Map<String,Object> parameters) {
        publishMenuEvent(
                NewCustomizeEventDto.builder()
                        .eventName("MENU")
                        .username(username)
                        .description(description)
                        .menuOptions(options)
                        .parameters(parameters)
                        .build()
        );
    }

}
