package gradutionProject.IMUISystem.eventExecutor.request;

import gradutionProject.IMUISystem.eventExecutor.entity.APIData;

import java.util.Map;

public interface RestRequestService {
    void sendEventRequest(APIData apiData, Map<String,String> variables);
}
