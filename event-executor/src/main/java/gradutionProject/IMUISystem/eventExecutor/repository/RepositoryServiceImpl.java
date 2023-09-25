package gradutionProject.IMUISystem.eventExecutor.repository;

import gradutionProject.IMUISystem.eventExecutor.entity.APIData;
import gradutionProject.IMUISystem.eventExecutor.entity.EventType;
import gradutionProject.IMUISystem.eventExecutor.entity.NotifyData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RepositoryServiceImpl implements RepositoryService{
    private final APIDataRepository apiDataRepository;
    private final NotifyDataRepository notifyDataRepository;

    @Override
    public EventType getEventType(String eventName) {
        if(apiDataRepository.existsById(eventName)) return EventType.API;
        return null;
    }

    @Override
    public APIData getApiDataByEventName(String eventName) {
        if(!apiDataRepository.existsById(eventName)) return null;
        return apiDataRepository.getReferenceById(eventName);
    }

    @Override
    public boolean isNotifyDataExist(String eventName) {
        return notifyDataRepository.existsById(eventName);
    }

    @Override
    public NotifyData getNotifyData(String eventName) {
        if(!notifyDataRepository.existsById(eventName)) return null;
        return notifyDataRepository.getReferenceById(eventName);
    }
}
