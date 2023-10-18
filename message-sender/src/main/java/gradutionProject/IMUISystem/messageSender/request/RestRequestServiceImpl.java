package gradutionProject.IMUISystem.messageSender.request;

import gradutionProject.IMUISystem.messageSender.dto.GetIMDataDto;
import gradutionProject.IMUISystem.messageSender.dto.IMUserData;
import gradutionProject.IMUISystem.messageSender.dto.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestRequestServiceImpl implements RestRequestService {
    @Value("${my_env.loginTrackerUrl}")
    private String LOGIN_TRACKER_URL;

    private final RestTemplate restTemplate;
    @Override
    public List<IMUserData> getIMData(List<String> usernameList) {
        List<IMUserData> imUserDataList = new ArrayList<>();
        LoginUserDto loginUserDto;
        for(String username:usernameList){
            loginUserDto = restTemplate.getForObject(String.format("%s/users/%s",LOGIN_TRACKER_URL,username), LoginUserDto.class);
            if(loginUserDto==null||loginUserDto.getImUserData()==null) continue;
            imUserDataList.add(loginUserDto.getImUserData());
        }
        return imUserDataList;
    }
}
