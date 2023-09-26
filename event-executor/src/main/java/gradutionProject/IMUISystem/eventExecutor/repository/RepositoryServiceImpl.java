package gradutionProject.IMUISystem.eventExecutor.repository;

import gradutionProject.IMUISystem.eventExecutor.entity.CommConfig;
import gradutionProject.IMUISystem.eventExecutor.entity.NotifyConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RepositoryServiceImpl implements RepositoryService{
    private final CommConfigRepository commConfigRepository;
    private final NotifyDataRepository notifyDataRepository;

    @Override
    public boolean isEventExist(String eventName) {
        return commConfigRepository.existsById(eventName);
    }

    @Override
    public CommConfig getCommConfig(String eventName) {
        if(!commConfigRepository.existsById(eventName)) return null;
        return commConfigRepository.getReferenceById(eventName);
    }

    @Override
    public boolean isNotifyDataExist(String eventName) {
        return notifyDataRepository.existsById(eventName);
    }

    @Override
    public NotifyConfig getNotifyData(String eventName) {
        if(!notifyDataRepository.existsById(eventName)) return null;
        return notifyDataRepository.getReferenceById(eventName);
    }
}
