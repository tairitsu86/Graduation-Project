package gradutionProject.IMUISystem.eventHandler.repository;

import gradutionProject.IMUISystem.eventHandler.controller.exception.EventAlreadyExistException;
import gradutionProject.IMUISystem.eventHandler.controller.exception.EventNotExistException;
import gradutionProject.IMUISystem.eventHandler.entity.APIData;
import gradutionProject.IMUISystem.eventHandler.entity.CustomizeEvent;
import gradutionProject.IMUISystem.eventHandler.entity.IMUserData;
import gradutionProject.IMUISystem.eventHandler.entity.UserState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RepositoryServiceImpl implements RepositoryService{
    private final CustomizeEventRepository customizeEventRepository;
    private final UserStateRepository userStateRepository;
    @Override
    public boolean isUserHaveState(IMUserData imUserData) {
        return userStateRepository.existsById(imUserData);
    }

    @Override
    public void newUserState(UserState userState) {
        userStateRepository.save(userState);
    }

    @Override
    public void removeUserState(IMUserData imUserData) {
        userStateRepository.deleteById(imUserData);
    }

    @Override
    public UserState getUserState(IMUserData imUserData) {
        if(!userStateRepository.existsById(imUserData)) return null;
        return userStateRepository.getReferenceById(imUserData);
    }

    @Override
    public boolean checkEventName(String eventName) {
        return customizeEventRepository.existsById(eventName);
    }

    @Override
    public List<String> getEventVariables(String eventName) {
        if(!customizeEventRepository.existsById(eventName)) return null;
//        return customizeEventRepository.getReferenceById(eventName).getVariables();
        return customizeEventRepository.getEventVariables(eventName);
    }

    @Override
    public APIData getAPIData(String eventName) {
        if(!customizeEventRepository.existsById(eventName)) return null;
        return customizeEventRepository.getReferenceById(eventName).getApiData();
    }

    @Override
    public List<String> getAllEvent() {
        return customizeEventRepository.getAllEventName();
    }

    @Override
    public CustomizeEvent getEvent(String eventName) {
        if(!customizeEventRepository.existsById(eventName)) throw new EventNotExistException(eventName);
        return customizeEventRepository.getReferenceById(eventName);
    }

    @Override
    public void newEvent(CustomizeEvent customizeEvent) {
        if(customizeEventRepository.existsById(customizeEvent.getEventName())) throw new EventAlreadyExistException(customizeEvent.getEventName());
        customizeEventRepository.save(customizeEvent);
    }


    @Override
    public void alterEvent(String eventName, String description, APIData apiData, List<String> variables) {
        CustomizeEvent event = getEvent(eventName);
        if(description!=null)
            event.setDescription(description);
        if(apiData!=null)
            event.setApiData(apiData);
        if(variables!=null)
            event.setVariables(variables);
        customizeEventRepository.save(event);
    }

    @Override
    public void deleteEvent(String eventName) {
        customizeEventRepository.deleteById(eventName);
    }
}
