package graduationProject.IMUISystem.eventHandler.request;

import graduationProject.IMUISystem.eventHandler.controller.exception.HttpApiException;
import graduationProject.IMUISystem.eventHandler.dto.LoginUserDto;
import graduationProject.IMUISystem.eventHandler.dto.UserLoginDto;
import graduationProject.IMUISystem.eventHandler.dto.UserSignUpDto;
import graduationProject.IMUISystem.eventHandler.entity.IMUserData;
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
            log.info("getUsername got error: {}", e.getMessage());
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
            log.info("getIMUserData got error: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public String login(UserLoginDto userLoginDto) {
        try{
            ResponseEntity<String> response = restTemplate.postForEntity(String.format("%s/login",USER_DATABASE_URL),userLoginDto,String.class);
            if(response.getStatusCode().is2xxSuccessful()) return "Login success!";
        }catch (HttpClientErrorException e){
            if(e.getStatusCode().is4xxClientError()) return "Login failed!\nWrong username or password!";
            if(e.getStatusCode().is5xxServerError()) return "Server error, please try again after few minutes!";
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
            if(e.getStatusCode().is4xxClientError()) return "Sign up failed!\nUsername has already been taken!";
            if(e.getStatusCode().is5xxServerError()) return "Server error, please try again after few minutes!";
            log.info("Sign up Error: {}", e.getMessage());
        }
        return "Something go wrong, please try again after few minutes!";
    }

    @Override
    public void newCommConfig(String eventName,Object commConfigDto) {
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
    public Object getCommConfig(String eventName) {
        try{
            ResponseEntity<Object> response = restTemplate.getForEntity(String.format("%s/events/%s/comm",EVENT_EXECUTOR_URL,eventName),Object.class);
            if(response.getStatusCode().is2xxSuccessful()) return response.getBody();
        }catch (HttpClientErrorException e){
            log.info("getCommConfig Error: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public void newRespondConfig(String eventName, Object respondConfigDto) {
        try{
            ResponseEntity<String> response = restTemplate.postForEntity(String.format("%s/events/%s/respond/new",EVENT_EXECUTOR_URL,eventName),respondConfigDto,String.class);
            if(!response.getStatusCode().is2xxSuccessful()) throw new HttpApiException(response.getBody());
        }catch (HttpClientErrorException e){
            log.info("newRespondConfig Error: {}", e.getMessage());
        }
    }

    @Override
    public void deleteRespondConfig(String eventName) {
        try{
            restTemplate.delete(String.format("%s/events/%s/respond/delete",EVENT_EXECUTOR_URL,eventName));
        }catch (HttpClientErrorException e){
            log.info("deleteRespondConfig Error: {}", e.getMessage());
        }
    }

    @Override
    public Object getRespondConfig(String eventName) {
        try{
            ResponseEntity<Object> response = restTemplate.getForEntity(String.format("%s/events/%s/respond",EVENT_EXECUTOR_URL,eventName),Object.class);
            if(response.getStatusCode().is2xxSuccessful()) return response.getBody();
        }catch (HttpClientErrorException e){
            log.info("getRespondConfig Error: {}", e.getMessage());
        }
        return null;
    }
}
