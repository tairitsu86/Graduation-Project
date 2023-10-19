package graduationProject.IMUISystem.messageSender.request;

import graduationProject.IMUISystem.messageSender.dto.IMUserData;

import java.util.List;

public interface RestRequestService {
    List<IMUserData> getIMData(List<String> usernameList);
}
