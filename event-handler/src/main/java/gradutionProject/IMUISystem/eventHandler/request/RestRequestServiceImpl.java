package gradutionProject.IMUISystem.eventHandler.request;

import gradutionProject.IMUISystem.eventHandler.controller.exception.HttpApiException;
import gradutionProject.IMUISystem.eventHandler.dto.*;
import gradutionProject.IMUISystem.eventHandler.entity.IMUserData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestRequestServiceImpl implements RestRequestService {
    @Value("${my_env.loginTrackerUrl}")
    private final String LOGIN_TRACKER_URL;

    @Value("${my_env.eventExecutorUrl}")
    private final String EVENT_EXECUTOR_URL;

    @Value("${my_env.userDatabaseUrl}")
    private final String USER_DATABASE_URL;

    private final RestTemplate restTemplate;

    @Override
    public String getUsername(IMUserData imUserData) {
        try{
            ResponseEntity<GetUsernameDto> response = restTemplate.getForEntity(
                    String.format("%s/%s/users/%s", LOGIN_TRACKER_URL, imUserData.getPlatform(),imUserData.getUserId())
                    ,GetUsernameDto.class
            );
            return response.getBody().getUsername();
        }catch (HttpClientErrorException e){
            return null;
        }
    }

    @Override
    public void login(LoginEventDto loginEventDto) {
        try{
            restTemplate.postForEntity(String.format("%s/login",USER_DATABASE_URL),loginEventDto,String.class);
        }catch (HttpClientErrorException e){
            log.info("Login Error: {}",e);
        }
    }

    @Override
    public void signUp(AddUserDto addUserDto) {
        try{
            restTemplate.postForEntity(String.format("%s/users/new",USER_DATABASE_URL),addUserDto,String.class);
        }catch (HttpClientErrorException e){
            log.info("Sign up Error: {}",e);
        }
    }

    @Override
    public void newCommConfig(String eventName,CommConfigDto commConfigDto) {
        try{
            ResponseEntity<String> response = restTemplate.postForEntity(String.format("%s/events/%s/comm/new",EVENT_EXECUTOR_URL,eventName),commConfigDto,String.class);
            if(!response.getStatusCode().is2xxSuccessful()) throw new HttpApiException(response.getBody());
        }catch (HttpClientErrorException e){
            log.info("newCommConfig Error: {}",e);
        }
    }

    @Override
    public void deleteCommConfig(String eventName) {
        try{
            restTemplate.delete(String.format("%s/events/%s/comm/delete",EVENT_EXECUTOR_URL,eventName));
        }catch (HttpClientErrorException e){
            log.info("deleteCommConfig Error: {}",e);
        }
    }

    @Override
    public void newNotifyConfig(String eventName,NotifyConfigDto notifyConfigDto) {
        try{
            ResponseEntity<String> response = restTemplate.postForEntity(String.format("%s/events/%s/notify/new",EVENT_EXECUTOR_URL),notifyConfigDto,String.class);
            if(!response.getStatusCode().is2xxSuccessful()) throw new HttpApiException(response.getBody());
        }catch (HttpClientErrorException e){
            log.info("newNotifyConfig Error: {}",e);
        }
    }

    @Override
    public void deleteNotifyConfig(String eventName) {
        try{
            restTemplate.delete(String.format("%s/events/%s/notify/delete",EVENT_EXECUTOR_URL,eventName));
        }catch (HttpClientErrorException e){
            log.info("deleteNotifyConfig Error: {}",e);
        }
    }
}
