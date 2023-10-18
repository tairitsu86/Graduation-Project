package graduationProject.IoTSystem.deviceConnector.controller;

import graduationProject.IoTSystem.deviceConnector.dto.DeviceStateSimpleDto;
import graduationProject.IoTSystem.deviceConnector.repository.DeviceStateHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DeviceConnectorController {
    private final DeviceStateHistoryRepository deviceStateHistoryRepository;
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String home(){
        return "This is Device Controller!";
    }

    @GetMapping("/devices/{deviceId}/state")
    private Object getDeviceState(@PathVariable String deviceId){
//        return deviceStateHistoryRepository.getDeviceAllStates(deviceId);
        return null;
    }
}












