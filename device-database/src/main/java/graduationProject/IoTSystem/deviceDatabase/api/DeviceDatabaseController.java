package graduationProject.IoTSystem.deviceDatabase.api;

import graduationProject.IoTSystem.deviceDatabase.dto.GetDeviceDto;
import graduationProject.IoTSystem.deviceDatabase.dto.GetDevicesDto;
import graduationProject.IoTSystem.deviceDatabase.rabbitMQ.MQEventPublisher;
import graduationProject.IoTSystem.deviceDatabase.repository.DeviceRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DeviceDatabaseController {
    private final DeviceRepositoryService deviceRepositoryService;
    private final MQEventPublisher mqEventPublisher;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String home(){
        return "This is Device Database!";
    }

    @GetMapping("/users/{username}/devices")
    @ResponseStatus(HttpStatus.OK)
    public List<GetDevicesDto> getDevices(@PathVariable String username){
        return deviceRepositoryService.getDevices(username);
    }
    @GetMapping("/devices/{deviceId}")
    @ResponseStatus(HttpStatus.OK)
    public GetDeviceDto getDevice(@PathVariable String deviceId){
        return deviceRepositoryService.getDevice(deviceId);
    }
    @GetMapping("/devices/new/id")
    public String newDeviceId(){
        return UUID.randomUUID().toString();
    }

}











