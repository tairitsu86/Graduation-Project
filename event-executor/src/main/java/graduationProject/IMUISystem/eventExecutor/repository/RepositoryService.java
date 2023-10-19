package graduationProject.IMUISystem.eventExecutor.repository;

import graduationProject.IMUISystem.eventExecutor.entity.CommConfig;
import graduationProject.IMUISystem.eventExecutor.entity.RespondConfig;

public interface RepositoryService {

    void newCommConfig(CommConfig commConfig);
    void deleteCommConfig(String eventName);
    boolean isCommConfigExist(String eventName);
    CommConfig getCommConfig(String eventName);

    void newRespondConfig(RespondConfig respondConfig);
    void deleteRespondConfig(String eventName);
    boolean isRespondConfigExist(String eventName);
    RespondConfig getRespondConfig(String eventName);
}
