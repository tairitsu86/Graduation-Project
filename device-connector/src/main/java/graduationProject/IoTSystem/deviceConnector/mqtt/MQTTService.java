package graduationProject.IoTSystem.deviceConnector.mqtt;

public interface MQTTService {
    void controlDevice(String deviceId,int functionId,String functionPara);
}
