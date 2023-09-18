package gradutionProject.IMUISystem.eventExecutor.request;

import gradutionProject.IMUISystem.eventExecutor.entity.APIData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RestRequestServiceImpl implements RestRequestService {

    private final RestTemplate restTemplate;

    @Override
    public void sendEventRequest(APIData apiData, Map<String, String> variables) {
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
            default -> {return;}
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(requestBody,headers);
        try{
            restTemplate.exchange(url,httpMethod,entity,String.class);
        }catch (HttpClientErrorException e){
            System.err.printf("Custom API Error,url[%s],body[%s],error type[%s]\n",url,requestBody,e.getMessage());
        }
    }
}
