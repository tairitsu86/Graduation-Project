package gradutionProject.IoTSystem.deviceDatabase.request;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class RestRequestServiceImpl implements RestRequestService {
    private final RestTemplate restTemplate;
}
