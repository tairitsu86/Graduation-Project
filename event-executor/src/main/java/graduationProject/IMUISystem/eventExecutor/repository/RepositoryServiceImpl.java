package graduationProject.IMUISystem.eventExecutor.repository;

import graduationProject.IMUISystem.eventExecutor.entity.CommConfig;
import graduationProject.IMUISystem.eventExecutor.entity.RespondConfig;
import graduationProject.IMUISystem.eventExecutor.controller.exception.CommConfigAlreadyExistException;
import graduationProject.IMUISystem.eventExecutor.controller.exception.RespondConfigAlreadyExistException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RepositoryServiceImpl implements RepositoryService{
    private final CommConfigRepository commConfigRepository;
    private final RespondConfigRepository respondConfigRepository;

    @PostConstruct
    public void init() {

    }

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
    public void newRespondConfig(RespondConfig respondConfig) {
        if(respondConfigRepository.existsById(respondConfig.getEventName()))
            throw new RespondConfigAlreadyExistException(respondConfig.getEventName());
        respondConfigRepository.save(respondConfig);
    }

    @Override
    public void deleteRespondConfig(String eventName) {
        respondConfigRepository.deleteById(eventName);
    }

    @Override
    public boolean isRespondConfigExist(String eventName) {
        return respondConfigRepository.existsById(eventName);
    }

    @Override
    public RespondConfig getRespondConfig(String eventName) {
        if(!respondConfigRepository.existsById(eventName)) return null;
        return respondConfigRepository.getReferenceById(eventName);
    }
}
