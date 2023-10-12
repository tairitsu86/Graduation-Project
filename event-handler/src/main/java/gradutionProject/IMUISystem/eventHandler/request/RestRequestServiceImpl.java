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

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestRequestServiceImpl implements RestRequestService {
    @Value("${my_env.loginTrackerUrl}")
    private String LOGIN_TRACKER_URL;

    @Value("${my_env.eventExecutorUrl}")
    private String EVENT_EXECUTOR_URL;

    @Value("${my_env.userDatabaseUrl}")
    private String USER_DATABASE_URL;

    private final RestTemplate restTemplate;

    @Override
    public String getUsername(IMUserData imUserData) {
        try{
            ResponseEntity<LoginUserDto> response = restTemplate.getForEntity(
                    String.format("%s/%s/users/%s", LOGIN_TRACKER_URL, imUserData.getPlatform(),imUserData.getUserId())
                    ,LoginUserDto.class
            );
            return Objects.requireNonNull(response.getBody()).getUsername();
        }catch (HttpClientErrorException e){
            log.info("getUsername got error: {}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    public IMUserData getIMUserData(String username) {
        try{
            ResponseEntity<LoginUserDto> response = restTemplate.getForEntity(
                    String.format("%s/users/%s", LOGIN_TRACKER_URL, username)
                    ,LoginUserDto.class
            );
            return Objects.requireNonNull(response.getBody()).getImUserData();
        }catch (HttpClientErrorException e){
            log.info("getIMUserData got error: {}", e.getMessage(),e);
            return null;
        }
    }

    @Override
    public String login(UserLoginDto userLoginDto) {
        try{
            ResponseEntity<String> response = restTemplate.postForEntity(String.format("%s/login",USER_DATABASE_URL),userLoginDto,String.class);
            if(response.getStatusCode().is2xxSuccessful()) return "Login success!";
        }catch (HttpClientErrorException e){
            log.info("Login Error: {}", e.getMessage());
        }
        return "Something go wrong, please try again after few minutes!";
    }

    @Override
    public String signUp(UserSignUpDto userSignUpDto) {
        try{
            ResponseEntity<String> response = restTemplate.postForEntity(String.format("%s/users/new",USER_DATABASE_URL), userSignUpDto,String.class);
            if(response.getStatusCode().is2xxSuccessful()) return "Sign up success! Now you can login!";
        }catch (HttpClientErrorException e){
            log.info("Sign up Error: {}", e.getMessage());
        }
        return "Something go wrong, please try again after few minutes!";
    }

    @Override
    public void newCommConfig(String eventName,CommConfigDto commConfigDto) {
        try{
            ResponseEntity<String> response = restTemplate.postForEntity(String.format("%s/events/%s/comm/new",EVENT_EXECUTOR_URL,eventName),commConfigDto,String.class);
            if(!response.getStatusCode().is2xxSuccessful()) throw new HttpApiException(response.getBody());
        }catch (HttpClientErrorException e){
            log.info("newCommConfig Error: {}", e.getMessage());
        }
    }

    @Override
    public void deleteCommConfig(String eventName) {
        try{
            restTemplate.delete(String.format("%s/events/%s/comm/delete",EVENT_EXECUTOR_URL,eventName));
        }catch (HttpClientErrorException e){
            log.info("deleteCommConfig Error: {}", e.getMessage());
        }
    }

    @Override
    public CommConfigDto getCommConfig(String eventName) {
        try{
            ResponseEntity<CommConfigDto> response = restTemplate.getForEntity(String.format("%s/events/%s/comm",EVENT_EXECUTOR_URL,eventName),CommConfigDto.class);
            if(response.getStatusCode().is2xxSuccessful()) return response.getBody();
        }catch (HttpClientErrorException e){
            log.info("getCommConfig Error: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public void newNotifyConfig(String eventName,NotifyConfigDto notifyConfigDto) {
        try{
            ResponseEntity<String> response = restTemplate.postForEntity(String.format("%s/events/%s/notify/new",EVENT_EXECUTOR_URL),notifyConfigDto,String.class);
            if(!response.getStatusCode().is2xxSuccessful()) throw new HttpApiException(response.getBody());
        }catch (HttpClientErrorException e){
            log.info("newNotifyConfig Error: {}", e.getMessage());
        }
    }

    @Override
    public void deleteNotifyConfig(String eventName) {
        try{
            restTemplate.delete(String.format("%s/events/%s/notify/delete",EVENT_EXECUTOR_URL,eventName));
        }catch (HttpClientErrorException e){
            log.info("deleteNotifyConfig Error: {}", e.getMessage());
        }
    }

    @Override
    public NotifyConfigDto getNotifyConfig(String eventName) {
        try{
            ResponseEntity<NotifyConfigDto> response = restTemplate.getForEntity(String.format("%s/events/%s/notify",EVENT_EXECUTOR_URL,eventName),NotifyConfigDto.class);
            if(response.getStatusCode().is2xxSuccessful()) return response.getBody();
        }catch (HttpClientErrorException e){
            log.info("getNotifyConfig Error: {}", e.getMessage());
        }
        return null;
    }
}
