package gradutionProject.IMUISystem.eventHandler.request;

import gradutionProject.IMUISystem.eventHandler.dto.GetUsernameDto;
import gradutionProject.IMUISystem.eventHandler.entity.APIData;
import gradutionProject.IMUISystem.eventHandler.entity.IMUserData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RestRequestServiceImpl implements RestRequestService {
    @Value("${my_env.loginTrackerUrl}")
    private String LOGIN_TRACKER_URL;

    private final RestTemplate restTemplate;

    @Override
    public String getUsername(IMUserData imUserData) {
        List<IMUserData> imUserDataList = new ArrayList<>();
        GetUsernameDto getUsernameDto = restTemplate.getForObject(
                String.format("%s/%s/users/%s", LOGIN_TRACKER_URL, imUserData.getPlatform(),imUserData.getUserId())
                ,GetUsernameDto.class
        );
        if(getUsernameDto==null||getUsernameDto.getUsername()==null)
            return null;
        return getUsernameDto.getUsername();
    }

    @Override
    public void sendEventRequest(APIData apiData, Map<String, String> variables) {
        String url = apiData.getUrlTemplate();
        String requestBody = apiData.getRequestBodyTemplate();
        if(requestBody==null) requestBody = "";
        for(String key:variables.keySet()){
            url.replaceAll(key,variables.get(key));
            requestBody.replaceAll(key,variables.get(key));
        }
        HttpMethod httpMethod;
        switch (apiData.getApiMethod()){
            case GET -> httpMethod = HttpMethod.GET;
            case PUT -> httpMethod = HttpMethod.PUT;
            case POST -> httpMethod = HttpMethod.POST;
            case PATCH -> httpMethod = HttpMethod.PATCH;
            case DELETE -> httpMethod = HttpMethod.DELETE;
            default -> {return;}
        }
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody);
        restTemplate.exchange(url,httpMethod,httpEntity,String.class);
    }
}
