package gradutionProject.IMUISystem.eventExecutor.repository;

import gradutionProject.IMUISystem.eventExecutor.entity.CommConfig;
import gradutionProject.IMUISystem.eventExecutor.entity.NotifyConfig;

public interface RepositoryService {
    boolean isEventExist(String eventName);
    CommConfig getCommConfig(String eventName);
    boolean isNotifyDataExist(String eventName);
    NotifyConfig getNotifyData(String eventName);
}
