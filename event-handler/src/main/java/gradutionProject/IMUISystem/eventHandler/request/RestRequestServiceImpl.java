package gradutionProject.IMUISystem.eventHandler.request;

import gradutionProject.IMUISystem.eventHandler.dto.*;
import gradutionProject.IMUISystem.eventHandler.entity.IMUserData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
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

    }

    @Override
    public void signUp(AddUserDto addUserDto) {

    }

    @Override
    public void newCommConfig(CommConfigDto commConfigDto) {

    }

    @Override
    public void deleteCommConfig(String eventName) {

    }

    @Override
    public void newNotifyConfig(NotifyConfigDto notifyConfigDto) {

    }

    @Override
    public void deleteNotifyConfig(String eventName) {

    }
}
