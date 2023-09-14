package graduationProject.IoTSystem.deviceConnector.rabbitMQ;


import graduationProject.IoTSystem.deviceConnector.dto.DeviceInfoDto;
import graduationProject.IoTSystem.deviceConnector.dto.DeviceStateDto;

public interface MQEventPublisher {
    void publishDeviceStateEvent(DeviceStateDto deviceStateDto);

    void publishDeviceInfoEvent(DeviceInfoDto deviceInfoDto);
}
