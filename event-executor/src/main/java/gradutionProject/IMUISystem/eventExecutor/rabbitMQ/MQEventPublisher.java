package gradutionProject.IMUISystem.eventExecutor.rabbitMQ;


import gradutionProject.IMUISystem.eventExecutor.entity.NotifyData;

import java.util.List;

public interface MQEventPublisher {
    void notifyUser(List<String> users, NotifyData notifyData, String json);
}
