package graduationProject.IoTSystem.deviceDatabase.rabbitMQ;

import graduationProject.IoTSystem.deviceDatabase.dto.DeviceControlDto;

public interface MQEventPublisher {
    void publishDeviceControl(DeviceControlDto deviceControlDto);
}
