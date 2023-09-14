package graduationProject.IoTSystem.deviceConnector.mqtt;

import graduationProject.IoTSystem.deviceConnector.dto.DeviceControlDto;
import graduationProject.IoTSystem.deviceConnector.dto.DeviceStateDto;

public interface MQTTService {
    void controlDevice(DeviceControlDto deviceControlDto);
}
