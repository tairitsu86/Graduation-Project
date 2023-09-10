package gradutionProject.IoTSystem.deviceDatabase.request;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RestRequestServiceImpl implements RestRequestService {
    @Value("${my_env.loginTrackerUrl}")
    private String LOGIN_TRACKER_URL;

    private final RestTemplate restTemplate;
}
