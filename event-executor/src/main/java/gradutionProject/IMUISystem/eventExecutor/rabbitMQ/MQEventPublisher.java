package gradutionProject.IMUISystem.eventExecutor.rabbitMQ;


import gradutionProject.IMUISystem.eventExecutor.dto.CommConfigDto;
import gradutionProject.IMUISystem.eventExecutor.entity.NotifyConfig;

import java.util.List;
import java.util.Map;

public interface MQEventPublisher {
    void notifyUser(List<String> users, NotifyConfig notifyConfig, String json);

    void publishCustomEvent(CommConfigDto commConfigDto);
}
