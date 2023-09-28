package gradutionProject.IMUISystem.eventExecutor.repository;

import gradutionProject.IMUISystem.eventExecutor.controller.exception.CommConfigAlreadyExistException;
import gradutionProject.IMUISystem.eventExecutor.controller.exception.NotifyConfigAlreadyExistException;
import gradutionProject.IMUISystem.eventExecutor.entity.CommConfig;
import gradutionProject.IMUISystem.eventExecutor.entity.NotifyConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RepositoryServiceImpl implements RepositoryService{
    private final CommConfigRepository commConfigRepository;
    private final NotifyConfigRepository notifyConfigRepository;

    @Override
    public void newCommConfig(CommConfig commConfig) {
        if(commConfigRepository.existsById(commConfig.getEventName()))
            throw new CommConfigAlreadyExistException(commConfig.getEventName());
        commConfigRepository.save(commConfig);
    }

    @Override
    public void deleteCommConfig(String eventName) {
        commConfigRepository.deleteById(eventName);
    }

    @Override
    public boolean isCommConfigExist(String eventName) {
        return commConfigRepository.existsById(eventName);
    }

    @Override
    public CommConfig getCommConfig(String eventName) {
        if(!commConfigRepository.existsById(eventName)) return null;
        return commConfigRepository.getReferenceById(eventName);
    }

    @Override
    public void newNotifyConfig(NotifyConfig notifyConfig) {
        if(notifyConfigRepository.existsById(notifyConfig.getEventName()))
            throw new NotifyConfigAlreadyExistException(notifyConfig.getEventName());
        notifyConfigRepository.save(notifyConfig);
    }

    @Override
    public void deleteNotifyConfig(String eventName) {
        notifyConfigRepository.deleteById(eventName);
    }

    @Override
    public boolean isNotifyConfigExist(String eventName) {
        return notifyConfigRepository.existsById(eventName);
    }

    @Override
    public NotifyConfig getNotifyConfig(String eventName) {
        if(!notifyConfigRepository.existsById(eventName)) return null;
        return notifyConfigRepository.getReferenceById(eventName);
    }
}
