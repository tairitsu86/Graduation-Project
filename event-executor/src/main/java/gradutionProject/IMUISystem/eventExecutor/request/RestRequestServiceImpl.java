package gradutionProject.IMUISystem.eventExecutor.request;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gradutionProject.IMUISystem.eventExecutor.dto.CommConfigDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestRequestServiceImpl implements RestRequestService {
    private final ObjectMapper objectMapper;
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
        Map<String,String> header;
        try{
            header = objectMapper.readValue(commConfigDto.getHeader(),new TypeReference<Map<String,String>>() {});
        } catch (Exception e) {
            log.info("Header mapper error: {}",e.getMessage(),e);
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setAll(header);
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
