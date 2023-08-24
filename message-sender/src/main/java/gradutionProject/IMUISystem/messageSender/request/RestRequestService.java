package gradutionProject.IMUISystem.messageSender.request;

import gradutionProject.IMUISystem.messageSender.dto.IMUserData;

import java.util.List;

public interface RestRequestService {
    List<IMUserData> getIMData(List<String> usernameList);
}
