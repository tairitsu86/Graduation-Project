package gradutionProject.IoTSystem.deviceconnector.mqtt;

public interface MQTTService {
    void controlDevice(String deviceId,int functionId,String functionPara);
}
