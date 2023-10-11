package gradutionProject.IMUISystem.eventExecutor.rabbitMQ;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import gradutionProject.IMUISystem.eventExecutor.dto.CommConfigDto;
import gradutionProject.IMUISystem.eventExecutor.dto.SendingEventDto;
import gradutionProject.IMUISystem.eventExecutor.entity.NotifyConfig;
import gradutionProject.IMUISystem.eventExecutor.entity.NotifyVariable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static gradutionProject.IMUISystem.eventExecutor.rabbitMQ.RabbitmqConfig.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MQEventPublisherImpl implements MQEventPublisher{
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public void publishSendingEvent(SendingEventDto sendingEventDto) {
        rabbitTemplate.convertAndSend(IMUI_IBC_EXCHANGE, SEND_MESSAGE_QUEUE,sendingEventDto);
    }

    @Override
    public void notifyUser(List<String> users, NotifyConfig notifyConfig, String json) {
        publishSendingEvent(
                SendingEventDto.builder()
                        .usernameList(users)
                        .message(getMessage(notifyConfig,json))
                        .build());
    }

    @Override
    public void publishCustomEvent(CommConfigDto commConfigDto) {
        try {
            Map<String, Object> jsonMap = objectMapper.readValue(commConfigDto.getBody(), new TypeReference<Map<String, Object>>() {});
            rabbitTemplate.convertAndSend(SYS_SVC_EXCHANGE, commConfigDto.getUrl(), jsonMap);
        } catch (Exception e) {
            log.info("publishCustomEvent convert json to Map<String,Object> got error: {}",e);
        }
    }


    public String getMessage(NotifyConfig notifyConfig, String json){
        Map<String,String> variables = new HashMap<>();
        String value;
        for(NotifyVariable notifyVariable: notifyConfig.getNotifyVariables()){
            switch (notifyVariable.getNotifyVariableType()){
                case ARRAY -> {
                    value = "";
                    List<String> temp = JsonPath.read(json,notifyVariable.getJsonPath());
                    for (String s:temp) {
                        value = String.format("%s%s,",value,s);
                    }
                }
                case SINGLE -> {
                    value = JsonPath.read(json,notifyVariable.getJsonPath());
                }
                default -> {continue;}
            }
            variables.put(notifyVariable.getVariableName(),value);
        }

        String message = notifyConfig.getRespondTemplate();
        for (String s:variables.keySet())
            message = message.replace(String.format("${%s}",s),variables.get(s));
        return message;
    }
}
