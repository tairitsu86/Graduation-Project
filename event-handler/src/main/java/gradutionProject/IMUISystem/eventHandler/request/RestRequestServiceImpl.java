package gradutionProject.IMUISystem.eventHandler.request;

import gradutionProject.IMUISystem.eventHandler.dto.GetUsernameDto;
import gradutionProject.IMUISystem.eventHandler.entity.APIData;
import gradutionProject.IMUISystem.eventHandler.entity.IMUserData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
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
    public boolean sendEventRequest(APIData apiData, Map<String, String> variables) {
        String url = apiData.getUrlTemplate();
        String requestBody = apiData.getRequestBodyTemplate();
        if(requestBody==null) requestBody = "";
        for(String key:variables.keySet()){
            String replaceValue = String.format("${%s}",key);
            url = url.replace(replaceValue,variables.get(key));
            requestBody = requestBody.replace(replaceValue,variables.get(key));
        }
        HttpMethod httpMethod;
        switch (apiData.getApiMethod()){
            case GET -> httpMethod = HttpMethod.GET;
            case PUT -> httpMethod = HttpMethod.PUT;
            case POST -> httpMethod = HttpMethod.POST;
            case PATCH -> httpMethod = HttpMethod.PATCH;
            case DELETE -> httpMethod = HttpMethod.DELETE;
            default -> {return false;}
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(requestBody,headers);
        try{
            restTemplate.exchange(url,httpMethod,entity,String.class);
            return true;
        }catch (HttpClientErrorException e){
            System.err.printf("Custom API Error,url[%s],body[%s],error type[%s]\n",url,requestBody,e.getMessage());
            return false;
        }
    }
}
