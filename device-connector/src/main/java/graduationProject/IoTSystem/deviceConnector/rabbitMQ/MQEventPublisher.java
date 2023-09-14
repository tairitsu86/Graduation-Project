package graduationProject.IoTSystem.deviceConnector.rabbitMQ;


import graduationProject.IoTSystem.deviceConnector.dto.DeviceStateDto;

public interface MQEventPublisher {
    void publishDeviceStateEvent(DeviceStateDto deviceStateDto);
}
