package gradutionProject.IMUISystem.messageSender.sender;

import gradutionProject.IMUISystem.messageSender.dto.IMUserData;
import gradutionProject.IMUISystem.messageSender.instantMessagingPlatform.IMService;
import gradutionProject.IMUISystem.messageSender.instantMessagingPlatform.InstantMessagingPlatform;
import gradutionProject.IMUISystem.messageSender.instantMessagingPlatform.line.LineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SendingServiceImpl implements SendingService {
    private final LineService lineService;

    @Override
    public void sendTextMessage(List<IMUserData> imUserDataList, String message) {
        if(imUserDataList==null) return;
        for (IMUserData imUserData:imUserDataList)
            sendTextMessage(imUserData,message);
    }

    @Override
    public void sendTextMessage(IMUserData imUserData, String message) {
        getIMService(imUserData.getPlatform()).SendTextMessage(imUserData.getUserId(), message);
    }

    public IMService getIMService(InstantMessagingPlatform instantMessagingPlatform){
        IMService result = null;
        switch (instantMessagingPlatform){
            case LINE -> result = lineService;
        }
        return result;
    }
}
