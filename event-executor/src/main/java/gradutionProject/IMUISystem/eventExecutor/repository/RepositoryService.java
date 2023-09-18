package gradutionProject.IMUISystem.eventExecutor.repository;

import gradutionProject.IMUISystem.eventExecutor.entity.APIData;
import gradutionProject.IMUISystem.eventExecutor.entity.EventType;

public interface RepositoryService {
    EventType getEventType(String eventName);
    APIData getApiDataByEventName(String eventName);
}
