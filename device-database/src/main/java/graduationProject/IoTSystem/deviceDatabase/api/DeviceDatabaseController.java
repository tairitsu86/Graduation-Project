package graduationProject.IoTSystem.deviceDatabase.api;

import graduationProject.IoTSystem.deviceDatabase.dto.GetDeviceDetailDto;
import graduationProject.IoTSystem.deviceDatabase.rabbitMQ.MQEventPublisher;
import graduationProject.IoTSystem.deviceDatabase.repository.RepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DeviceDatabaseController {
    private final RepositoryService repositoryService;
    private final MQEventPublisher mqEventPublisher;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String home(){
        return "This is Device Database!";
    }

    @GetMapping("/users/{username}/devices")
    @ResponseStatus(HttpStatus.OK)
    public Set<Map<String,String>> getDeviceByUsername(@PathVariable String username){
        return repositoryService.getDeviceByUsername(username);
    }
    @GetMapping("/devices/{deviceId}")
    @ResponseStatus(HttpStatus.OK)
    public GetDeviceDetailDto getDeviceDetailDto(@PathVariable String deviceId, @RequestParam(required = false) String executor){
        return repositoryService.getDeviceDetail(deviceId, executor);
    }

    @GetMapping("/devices/new/id")
    public String newDeviceId(){
        return UUID.randomUUID().toString();
    }

}











