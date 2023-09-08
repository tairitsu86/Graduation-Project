package gradutionProject.IoTSystem.deviceconnector.api;

import gradutionProject.IoTSystem.deviceconnector.mqtt.MQTTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeviceConnectorController {
    private final MQTTService mqttService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String home(){
        return "This is Device Controller!";
    }
    @PostMapping("devices/{deviceId}/functions/{functionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deviceFunction(){

    }

}












