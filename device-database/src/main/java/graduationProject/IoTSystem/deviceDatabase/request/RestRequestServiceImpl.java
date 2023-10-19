package graduationProject.IoTSystem.deviceDatabase.request;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RestRequestServiceImpl implements RestRequestService {
    private final RestTemplate restTemplate;
    @Value("${my_env.groupManagerUrl}")
    private String GROUP_MANAGER_URL;

    @Override
    public List<String> getGroupsByUsername(String username) {
        ResponseEntity<List<String>> response = restTemplate.exchange(String.format("%s/users/%s/groups",GROUP_MANAGER_URL,username), HttpMethod.GET,null,new ParameterizedTypeReference<List<String>>() {});
        return response.getBody();
    }
}
