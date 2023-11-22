package graduationProject.IMUISystem.messageSender.request;

import graduationProject.IMUISystem.messageSender.dto.IMUserData;
import graduationProject.IMUISystem.messageSender.dto.LoginUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
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
            try{
                loginUserDto = restTemplate.getForObject(String.format("%s/users/%s",LOGIN_TRACKER_URL,username), LoginUserDto.class);
                if(loginUserDto==null||loginUserDto.getImUserData()==null) continue;
                imUserDataList.add(loginUserDto.getImUserData());
            }catch (Exception e){
                log.info("getIMData error with: {}", e.getMessage(), e);
            }
        }


        return imUserDataList;
    }
}
