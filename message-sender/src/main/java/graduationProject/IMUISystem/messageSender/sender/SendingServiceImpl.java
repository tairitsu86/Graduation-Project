package graduationProject.IMUISystem.messageSender.sender;

import graduationProject.IMUISystem.messageSender.request.RestRequestService;
import graduationProject.IMUISystem.messageSender.dto.IMUserData;
import graduationProject.IMUISystem.messageSender.dto.SendingEventDto;
import graduationProject.IMUISystem.messageSender.dto.SendingServiceDto;
import graduationProject.IMUISystem.messageSender.rabbitMQ.MQEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SendingServiceImpl implements SendingService {
    private final RestRequestService restRequestService;
    private final MQEventPublisher mqEventPublisher;

    @Override
    public void sendingEvent(SendingEventDto sendingEventDto) {
        List<IMUserData> recipients = new ArrayList<>();
        if(sendingEventDto.getUsernameList()!=null)
            recipients.addAll(restRequestService.getIMData(sendingEventDto.getUsernameList()));
        if(sendingEventDto.getImUserDataList()!=null)
            recipients.addAll(sendingEventDto.getImUserDataList());
        Map<String,List<String>> sendingData = getSendingData(recipients);
        for (String platform:sendingData.keySet()) {
            mqEventPublisher.publishSendingServiceEvent(
                    platform,
                    SendingServiceDto.builder()
                            .userIdList(sendingData.get(platform))
                            .message(sendingEventDto.getMessage())
                            .build()
            );
        }
    }
    public Map<String,List<String>> getSendingData(List<IMUserData> recipients){
        Map<String,List<String>> data = new HashMap<>();
        String platform;
        String userId;
        for(IMUserData recipient: recipients){
            platform = recipient.getPlatform();
            userId = recipient.getUserId();
            if(!data.containsKey(platform))
                data.put(platform,new ArrayList<>());
            data.get(platform).add(userId);
        }
        return data;
    }


}
