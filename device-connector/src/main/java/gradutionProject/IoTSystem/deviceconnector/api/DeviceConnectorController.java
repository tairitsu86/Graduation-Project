package gradutionProject.IoTSystem.deviceconnector.api;

import gradutionProject.IoTSystem.deviceconnector.api.exception.DeviceNotFoundException;
import gradutionProject.IoTSystem.deviceconnector.mqtt.MQTTService;
import gradutionProject.IoTSystem.deviceconnector.repository.DeviceInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DeviceConnectorController {
    private final MQTTService mqttService;
    private final DeviceInfoRepository deviceInfoRepository;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String home(){
        return "This is Device Controller!";
    }

    @GetMapping("devices/{deviceId}/functions")
    @ResponseStatus(HttpStatus.OK)
    public List<String> deviceFunctions(@PathVariable String deviceId){
        if(!deviceInfoRepository.existsById(deviceId)) throw new DeviceNotFoundException(deviceId);
        return deviceInfoRepository.getFunctionNames(deviceId);
    }

    @PostMapping("devices/{deviceId}/functions/{functionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void controlDevice(@PathVariable String deviceId
            , @PathVariable int functionId
            , @RequestParam(required = false) String functionPara){
        if(!deviceInfoRepository.existsById(deviceId)) throw new DeviceNotFoundException(deviceId);
        mqttService.controlDevice(deviceId,functionId,functionPara);
    }

}












