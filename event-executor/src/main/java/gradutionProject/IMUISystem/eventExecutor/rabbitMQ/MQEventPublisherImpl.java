package gradutionProject.IMUISystem.eventExecutor.rabbitMQ;

import com.jayway.jsonpath.JsonPath;
import gradutionProject.IMUISystem.eventExecutor.dto.SendingEventDto;
import gradutionProject.IMUISystem.eventExecutor.entity.NotifyConfig;
import gradutionProject.IMUISystem.eventExecutor.entity.NotifyVariable;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static gradutionProject.IMUISystem.eventExecutor.rabbitMQ.RabbitmqConfig.sendingEventQueue;
import static gradutionProject.IMUISystem.eventExecutor.rabbitMQ.RabbitmqConfig.topicExchange;

@Service
@RequiredArgsConstructor
public class MQEventPublisherImpl implements MQEventPublisher{
    private final RabbitTemplate rabbitTemplate;

    public void publishSendingEvent(SendingEventDto sendingEventDto) {
        rabbitTemplate.convertAndSend(topicExchange, sendingEventQueue,sendingEventDto);
    }

    @Override
    public void notifyUser(List<String> users, NotifyConfig notifyConfig, String json) {
        publishSendingEvent(
                SendingEventDto.builder()
                        .usernameList(users)
                        .message(getMessage(notifyConfig,json))
                        .build());
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
