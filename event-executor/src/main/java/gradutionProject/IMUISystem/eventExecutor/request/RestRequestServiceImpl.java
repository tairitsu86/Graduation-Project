package gradutionProject.IMUISystem.eventExecutor.request;

import gradutionProject.IMUISystem.eventExecutor.dto.CommConfigDto;
import gradutionProject.IMUISystem.eventExecutor.entity.CommConfig;
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
    public ResponseEntity sendEventRequest(CommConfigDto commConfigDto) {
        HttpMethod httpMethod;
        switch (commConfigDto.getMethodType()){
            case GET -> httpMethod = HttpMethod.GET;
            case PUT -> httpMethod = HttpMethod.PUT;
            case POST -> httpMethod = HttpMethod.POST;
            case PATCH -> httpMethod = HttpMethod.PATCH;
            case DELETE -> httpMethod = HttpMethod.DELETE;
            default -> {return null;}
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(commConfigDto.getBody(),headers);
        try{
            return restTemplate.exchange(commConfigDto.getUrl(),httpMethod,entity,String.class);
        }catch (HttpClientErrorException e){
            System.err.printf("Custom API Error,url[%s],body[%s],error type[%s]\n",commConfigDto.getUrl(),commConfigDto.getBody(),e.getMessage());
        }
        return null;
    }
}
