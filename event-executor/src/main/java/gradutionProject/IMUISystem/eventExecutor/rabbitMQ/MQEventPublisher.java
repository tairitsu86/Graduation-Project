package gradutionProject.IMUISystem.eventExecutor.rabbitMQ;


import gradutionProject.IMUISystem.eventExecutor.entity.NotifyConfig;

import java.util.List;

public interface MQEventPublisher {
    void notifyUser(List<String> users, NotifyConfig notifyConfig, String json);
}
