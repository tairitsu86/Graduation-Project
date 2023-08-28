package gradutionProject.IMUISystem.eventHandler.request;

import gradutionProject.IMUISystem.eventHandler.entity.APIData;
import gradutionProject.IMUISystem.eventHandler.entity.IMUserData;

import java.util.Map;

public interface RestRequestService {
    String getUsername(IMUserData imUserData);
    boolean sendEventRequest(APIData apiData, Map<String,String> variables);
}