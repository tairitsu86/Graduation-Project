package graduationProject.IoTSystem.deviceDatabase.rabbitMQ;

import graduationProject.IoTSystem.deviceDatabase.dto.DeviceControlDto;
import graduationProject.IoTSystem.deviceDatabase.dto.DeviceStateDto;

public interface MQEventPublisher {
    void publishDeviceControl(DeviceControlDto deviceControlDto);
    void publishDeviceStateEvent(DeviceStateDto deviceStateDto);
}
