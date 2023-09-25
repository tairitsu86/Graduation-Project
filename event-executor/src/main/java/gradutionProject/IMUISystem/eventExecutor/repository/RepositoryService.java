package gradutionProject.IMUISystem.eventExecutor.repository;

import gradutionProject.IMUISystem.eventExecutor.entity.CommConfig;
import gradutionProject.IMUISystem.eventExecutor.entity.NotifyData;

public interface RepositoryService {
    boolean isEventExist(String eventName);
    CommConfig getCommConfig(String eventName);
    boolean isNotifyDataExist(String eventName);
    NotifyData getNotifyData(String eventName);
}
