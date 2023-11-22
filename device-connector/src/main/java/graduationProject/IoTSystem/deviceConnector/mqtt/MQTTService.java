package graduationProject.IoTSystem.deviceConnector.mqtt;

import graduationProject.IoTSystem.deviceConnector.dto.DeviceControlDto;

public interface MQTTService {
    void controlDevice(DeviceControlDto deviceControlDto);
}
