package graduationProject.IoTSystem.deviceConnector.controller;

import graduationProject.IoTSystem.deviceConnector.dto.NewDeviceDto;
import graduationProject.IoTSystem.deviceConnector.mqtt.MQTTConfig;
import graduationProject.IoTSystem.deviceConnector.repository.RepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DeviceConnectorController {
    private final RepositoryService repositoryService;
    private final MQTTConfig mqttConfig;
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String home(){
        return "This is Device Connector!";
    }

    @GetMapping("/devices/{deviceId}/states")
    private Object getDeviceStates(@PathVariable String deviceId){
        return repositoryService.getDeviceStates(deviceId);
    }

    @GetMapping("/devices/{deviceId}/states/{stateId}")
    private Object getDeviceStateData(@PathVariable String deviceId, @PathVariable int stateId){
        return repositoryService.getDeviceStateData(deviceId, stateId);
    }

    @GetMapping("/devices/{deviceId}/functions")
    private Object getDeviceFunctions(@PathVariable String deviceId){
        return repositoryService.getDeviceFunctions(deviceId);
    }
    @GetMapping("/devices/new")
    public NewDeviceDto newDevice(){
        return NewDeviceDto.builder()
                .id(UUID.randomUUID().toString())
                .mqttHostIp(mqttConfig.getMQTT_HOST_IP())
                .mqttHostPort(mqttConfig.getMQTT_HOST_PORT())
                .build();
    }
}












