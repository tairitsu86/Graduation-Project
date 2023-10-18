package graduationProject.IoTSystem.deviceConnector.controller;

import graduationProject.IoTSystem.deviceConnector.repository.DeviceStateHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class DeviceConnectorController {
    private final DeviceStateHistoryRepository deviceStateHistoryRepository;
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String home(){
        return "This is Device Controller!";
    }

    @GetMapping("/devices/{deviceId}/states")
    private Object getDeviceAllStates(@PathVariable String deviceId){
        return deviceStateHistoryRepository.getDeviceAllStates(deviceId);
    }
}












