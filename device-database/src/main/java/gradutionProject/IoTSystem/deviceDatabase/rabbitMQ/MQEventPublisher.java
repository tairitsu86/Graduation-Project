package gradutionProject.IoTSystem.deviceDatabase.rabbitMQ;

import gradutionProject.IoTSystem.deviceDatabase.dto.DeviceControlDto;

public interface MQEventPublisher {
    void publishDeviceControl(DeviceControlDto deviceControlDto);
}
