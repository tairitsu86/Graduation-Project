package gradutionProject.IMUISystem.eventExecutor.repository;

import gradutionProject.IMUISystem.eventExecutor.entity.APIData;
import gradutionProject.IMUISystem.eventExecutor.entity.EventType;
import gradutionProject.IMUISystem.eventExecutor.entity.NotifyData;

public interface RepositoryService {
    EventType getEventType(String eventName);
    APIData getApiDataByEventName(String eventName);

    NotifyData getNotifyData(String eventName);
}
