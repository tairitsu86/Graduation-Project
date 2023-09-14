package graduationProject.IoTSystem.deviceConnector.mqtt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MQTTServiceImpl implements MQTTService{

    private final MQTTTemplate mqttTemplate;
    @Override
    public void controlDevice(String deviceId, int functionId, String functionPara) {
        mqttTemplate.publish("Hi!");
    }

    public String controlTopic(String deviceId){
        return String.format("devices/%s/control",deviceId);
    }
}
