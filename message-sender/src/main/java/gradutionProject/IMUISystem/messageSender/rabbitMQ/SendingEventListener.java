package gradutionProject.IMUISystem.messageSender.rabbitMQ;

import gradutionProject.IMUISystem.messageSender.dto.GetIMDataDto;
import gradutionProject.IMUISystem.messageSender.dto.IMUserData;
import gradutionProject.IMUISystem.messageSender.dto.SendingEventDto;
import gradutionProject.IMUISystem.messageSender.sender.SendingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static gradutionProject.IMUISystem.messageSender.rabbitMQ.RabbitmqConfig.queueName;

@Slf4j
@RequiredArgsConstructor
@Component
public class SendingEventListener {
    private static final String LOGIN_TRACKER_URL = System.getenv("LOGIN_TRACKER_URL");
    private final RestTemplate restTemplate;
    private final SendingService sendingService;

    @RabbitListener(queues={queueName})
    public void receive(SendingEventDto sendingEventDto) {
        log.info("Sending Event:"+sendingEventDto.toString());
        List<IMUserData> recipients = new ArrayList<>();
        if(sendingEventDto.getUsernameList()!=null){
            GetIMDataDto getIMDataDto;
            for(String username:sendingEventDto.getUsernameList()){
                getIMDataDto = restTemplate.getForObject(String.format("%s/users/%s",LOGIN_TRACKER_URL,username), GetIMDataDto.class);
                if(getIMDataDto==null||getIMDataDto.getImUserDataList()==null) continue;
                recipients.addAll(getIMDataDto.getImUserDataList());
            }
        }
        if(sendingEventDto.getImUserDataList()!=null)
            recipients.addAll(sendingEventDto.getImUserDataList());
        sendingService.sendTextMessage(recipients,sendingEventDto.getMessage());
    }

    /* test data
    {
      "imUserData": {
        "platform": "LINE",
        "userId": "123"
      },
      "username": "OAO",
      "state": "LOGIN"
    }
    
    */
}
