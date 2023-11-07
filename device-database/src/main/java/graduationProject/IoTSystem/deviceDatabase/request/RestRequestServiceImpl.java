package graduationProject.IoTSystem.deviceDatabase.request;

import graduationProject.IoTSystem.deviceDatabase.entity.DeviceFunction;
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

    @Value("${my_env.deviceConnectorUrl}")
    private String DEVICE_CONNECTOR_URL;

    @Override
    public List<String> getGroupsByUsername(String username) {
        ResponseEntity<List<String>> response = restTemplate.exchange(String.format("%s/users/%s/groups",GROUP_MANAGER_URL,username), HttpMethod.GET,null,new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    @Override
    public List<DeviceFunction> getDeviceFunctions(String deviceId) {
        ResponseEntity<List<DeviceFunction>> response = restTemplate.exchange(String.format("%s/devices/%s/functions",DEVICE_CONNECTOR_URL,deviceId), HttpMethod.GET,null,new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    @Override
    public Object getDeviceStates(String deviceId) {
        ResponseEntity<Object> response = restTemplate.exchange(String.format("%s/devices/%s/states",DEVICE_CONNECTOR_URL,deviceId), HttpMethod.GET,null,new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    @Override
    public Object getDeviceStateData(String deviceId, int stateId) {
        ResponseEntity<Object> response = restTemplate.exchange(String.format("%s/devices/%s/states/%d",DEVICE_CONNECTOR_URL,deviceId,stateId), HttpMethod.GET,null,new ParameterizedTypeReference<>() {});
        return response.getBody();
    }
}
