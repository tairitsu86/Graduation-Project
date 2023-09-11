package gradutionProject.IoTSystem.deviceconnector.api;

import gradutionProject.IoTSystem.deviceconnector.api.exception.DeviceNotFoundException;
import gradutionProject.IoTSystem.deviceconnector.mqtt.MQTTService;
import gradutionProject.IoTSystem.deviceconnector.repository.DeviceStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class DeviceConnectorController {
    private final MQTTService mqttService;
    private final DeviceStateRepository deviceStateRepository;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String home(){
        return "This is Device Controller!";
    }

    @PostMapping("devices/{deviceId}/functions/{functionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void controlDevice(@PathVariable String deviceId
            , @PathVariable int functionId
            , @RequestParam(required = false) String functionPara){
        if(!deviceStateRepository.existsById(deviceId)) throw new DeviceNotFoundException(deviceId);
        mqttService.controlDevice(deviceId,functionId,functionPara);
    }

}












