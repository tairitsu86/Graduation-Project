package gradutionProject.IoTSystem.deviceconnector.api;

import gradutionProject.IoTSystem.deviceconnector.api.exception.DeviceStateNotFoundException;
import gradutionProject.IoTSystem.deviceconnector.dto.DeviceStateDto;
import gradutionProject.IoTSystem.deviceconnector.repository.DeviceStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DeviceConnectorController {
    private final DeviceStateRepository deviceStateRepository;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String home(){
        return "This is Device Controller!";
    }

    @GetMapping("devices/{deviceId}/states")
    @ResponseStatus(HttpStatus.OK)
    public List<DeviceStateDto> getState(@PathVariable String deviceId){
        return deviceStateRepository.getState(deviceId);
    }
    @GetMapping("devices/{deviceId}/states/{stateName}")
    @ResponseStatus(HttpStatus.OK)
    public DeviceStateDto getState(@PathVariable String deviceId, @PathVariable String stateName){
        List<DeviceStateDto> deviceStateDtoList = deviceStateRepository.getState(deviceId);
        if(deviceStateDtoList.size()==0) throw new DeviceStateNotFoundException(deviceId,stateName);
        return deviceStateRepository.getState(deviceId).get(0);
    }
}












