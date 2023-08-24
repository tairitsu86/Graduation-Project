package gradutionProject.IMUISystem.messageSender.sender;

import gradutionProject.IMUISystem.messageSender.dto.IMUserData;

import java.util.List;

public interface SendingService {
    void sendTextMessage(List<IMUserData> imUserDataList, String message);
    void sendTextMessage(IMUserData imUserData, String message);
}
