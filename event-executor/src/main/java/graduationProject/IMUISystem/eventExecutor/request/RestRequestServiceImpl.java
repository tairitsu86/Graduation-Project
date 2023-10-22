package graduationProject.IMUISystem.eventExecutor.request;

import graduationProject.IMUISystem.eventExecutor.dto.GetGroupUsersDto;
import graduationProject.IMUISystem.eventExecutor.dto.CommConfigDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestRequestServiceImpl implements RestRequestService {
    private final RestTemplate restTemplate;

    @Value("my_env.groupManagerUrl")
    private String GROUP_MANAGER_URL;

    @Override
    public ResponseEntity<String> sendEventRequest(CommConfigDto commConfigDto) {
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
        if(commConfigDto.getHeader()!=null)
            headers.setAll(commConfigDto.getHeader());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(commConfigDto.getBody(),headers);
        try{
            return restTemplate.exchange(commConfigDto.getUrl(),httpMethod,entity,String.class);
        }catch (HttpClientErrorException e){
            System.err.printf("Custom API Error,url[%s],body[%s],error type[%s]\n",commConfigDto.getUrl(),commConfigDto.getBody(),e.getMessage());
            return new ResponseEntity<>(e.getResponseBodyAsString(),e.getStatusCode());
        }
    }

    @Override
    public List<String> getGroupUsers(List<String> groups) {
        List<String> userList = new ArrayList<>();
        GetGroupUsersDto getGroupUsersDto;
        for(String group:groups){
            try{
                 getGroupUsersDto = restTemplate.getForObject(String.format("%s/groups/%s/users",GROUP_MANAGER_URL,group), GetGroupUsersDto.class);
                 if(getGroupUsersDto!=null&&getGroupUsersDto.getUsernameList()!=null)
                    userList.addAll(getGroupUsersDto.getUsernameList());
            }catch (HttpClientErrorException e){
                log.info(String.format("getGroupUsers API Error,url[%s],error type: {}",String.format("%s/groups/%s/users",GROUP_MANAGER_URL,group)),e.getMessage());
            }
        }
        return userList;
    }
}
