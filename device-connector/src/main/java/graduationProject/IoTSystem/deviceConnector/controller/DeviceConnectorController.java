package graduationProject.IoTSystem.deviceConnector.controller;

import graduationProject.IoTSystem.deviceConnector.dto.NewDeviceDto;
import graduationProject.IoTSystem.deviceConnector.mqtt.MQTTConfig;
import graduationProject.IoTSystem.deviceConnector.repository.DeviceStateHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DeviceConnectorController {
    private final DeviceStateHistoryRepository deviceStateHistoryRepository;
    private final MQTTConfig mqttConfig;
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String home(){
        return "This is Device Controller!";
    }

    @GetMapping("/devices/{deviceId}/states")
    private Object getDeviceAllStates(@PathVariable String deviceId){
        return deviceStateHistoryRepository.getDeviceAllStates(deviceId);
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












