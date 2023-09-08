package gradutionProject.IoTSystem.deviceconnector.mqtt;

import java.util.Map;

public interface MQTTService {
    void deviceFunction(String deviceId,int functionId,String a);
}
