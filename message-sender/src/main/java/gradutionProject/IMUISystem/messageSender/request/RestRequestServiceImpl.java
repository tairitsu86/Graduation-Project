package gradutionProject.IMUISystem.messageSender.request;

import gradutionProject.IMUISystem.messageSender.dto.GetIMDataDto;
import gradutionProject.IMUISystem.messageSender.dto.IMUserData;
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
        GetIMDataDto getIMDataDto;
        for(String username:usernameList){
//            String s = restTemplate.getForObject(String.format("%s/users/%s",LOGIN_TRACKER_URL,username), String.class);
//            System.out.println(s);
            getIMDataDto = restTemplate.getForObject(String.format("%s/users/%s",LOGIN_TRACKER_URL,username), GetIMDataDto.class);
            if(getIMDataDto==null||getIMDataDto.getImUserDataList()==null) continue;
            imUserDataList.addAll(getIMDataDto.getImUserDataList());
        }
        return imUserDataList;
    }
}
