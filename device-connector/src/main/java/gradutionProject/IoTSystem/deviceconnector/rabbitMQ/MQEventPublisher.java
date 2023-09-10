package gradutionProject.IoTSystem.deviceconnector.rabbitMQ;


import gradutionProject.IoTSystem.deviceconnector.dto.DeviceStateDto;

public interface MQEventPublisher {
    void publishDeviceStateEvent(DeviceStateDto deviceStateDto);
}
