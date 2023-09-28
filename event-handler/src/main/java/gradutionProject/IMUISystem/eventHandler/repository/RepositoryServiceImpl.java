package gradutionProject.IMUISystem.eventHandler.repository;

import gradutionProject.IMUISystem.eventHandler.controller.exception.EventAlreadyExistException;
import gradutionProject.IMUISystem.eventHandler.controller.exception.EventNotExistException;
import gradutionProject.IMUISystem.eventHandler.entity.CustomizeEvent;
import gradutionProject.IMUISystem.eventHandler.entity.IMUserData;
import gradutionProject.IMUISystem.eventHandler.entity.UserState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        return customizeEventRepository.getEventVariables(eventName);
    }

    @Override
    public List<String> getAllEvent() {
        List<String> allEvent = customizeEventRepository.getAllEventName();
        allEvent.add("LOGIN");
        allEvent.add("SIGN_UP");
        return allEvent;
    }

    @Override
    public CustomizeEvent getEvent(String eventName) {
        if(eventName.equals("LOGIN")) return createLoginBean();
        if(eventName.equals("SIGN_UP")) return createSignUpBean();
        if(!customizeEventRepository.existsById(eventName)) throw new EventNotExistException(eventName);
        return customizeEventRepository.getReferenceById(eventName);
    }

    @Override
    public void newEvent(CustomizeEvent customizeEvent) {
        if(customizeEventRepository.existsById(customizeEvent.getEventName())) throw new EventAlreadyExistException(customizeEvent.getEventName());
        customizeEventRepository.save(customizeEvent);
    }


    @Override
    public void deleteEvent(String eventName) {
        customizeEventRepository.deleteById(eventName);
    }

    public CustomizeEvent createLoginBean(){
        return CustomizeEvent.builder()
                .eventName("LOGIN")
                .description("")
                .variables(new ArrayList<>(){{add("USERNAME");add("PASSWORD");}})
                .build();
    }

    public CustomizeEvent createSignUpBean(){
        return CustomizeEvent.builder()
                .eventName("SIGN_UP")
                .description("")
                .variables(new ArrayList<>(){{add("USER_DISPLAY_NAME");add("USERNAME");add("PASSWORD");}})
                .build();
    }
}
