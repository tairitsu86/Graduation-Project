package gradutionProject.IMUISystem.messageSender.sender;

import gradutionProject.IMUISystem.messageSender.dto.IMUserData;

import java.util.List;

public class SendingServiceImpl implements SendingService {
    @Override
    public void sendTextMessage(List<IMUserData> imUserDataList, String message) {
        if(imUserDataList==null) return;
        for (IMUserData imUserData:imUserDataList)
            sendTextMessage(imUserData,message);
    }

    @Override
    public void sendTextMessage(IMUserData imUserData, String message) {
        imUserData.getPlatform().service.SendTextMessage(imUserData.getUserId(), message);
    }
}
