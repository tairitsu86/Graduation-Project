package gradutionProject.IMUISystem.eventExecutor.repository;

import gradutionProject.IMUISystem.eventExecutor.entity.CommConfig;
import gradutionProject.IMUISystem.eventExecutor.entity.NotifyConfig;

public interface RepositoryService {

    void newCommConfig(CommConfig commConfig);
    void deleteCommConfig(String eventName);
    boolean isCommConfigExist(String eventName);
    CommConfig getCommConfig(String eventName);

    void newNotifyConfig(NotifyConfig notifyConfig);
    void deleteNotifyConfig(String eventName);
    boolean isNotifyConfigExist(String eventName);
    NotifyConfig getNotifyConfig(String eventName);
}
