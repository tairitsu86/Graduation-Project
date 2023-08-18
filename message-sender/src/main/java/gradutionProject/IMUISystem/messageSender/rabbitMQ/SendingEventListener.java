package gradutionProject.IMUISystem.messageSender.rabbitMQ;

import gradutionProject.IMUISystem.messageSender.dto.IMUserData;
import gradutionProject.IMUISystem.messageSender.dto.SendingEventDto;
import gradutionProject.IMUISystem.messageSender.request.RestRequestService;
import gradutionProject.IMUISystem.messageSender.sender.SendingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static gradutionProject.IMUISystem.messageSender.rabbitMQ.RabbitmqConfig.queueName;

@Slf4j
@RequiredArgsConstructor
@Component
public class SendingEventListener {
    private final SendingService sendingService;
    private final RestRequestService restRequestService;

    @RabbitListener(queues={queueName})
    public void receive(SendingEventDto sendingEventDto) {
        log.info("Sending Event:"+sendingEventDto.toString());
        List<IMUserData> recipients = new ArrayList<>();
        if(sendingEventDto.getUsernameList()!=null)
            recipients.addAll(restRequestService.getIMData(sendingEventDto.getUsernameList()));
        if(sendingEventDto.getImUserDataList()!=null)
            recipients.addAll(sendingEventDto.getImUserDataList());
        sendingService.sendTextMessage(recipients,sendingEventDto.getMessage());
    }

    /* test data
    {
      "imUserDataList": [
        {
          "platform": "LINE",
          "userId": ""
        }
      ],
      "message": "HiHi"
    }
    {
      "usernameList": [
        "OAO"
      ],
      "message": "HiHi"
    }
    {
      "imUserDataList": [
        {
          "platform": "LINE",
          "userId": ""
        }
      ],
      "usernameList": [
        "OAO"
      ],
      "message": "HiHi"
    }
    */
}
