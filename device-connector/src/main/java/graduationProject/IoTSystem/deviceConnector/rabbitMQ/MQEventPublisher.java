package graduationProject.IoTSystem.deviceConnector.rabbitMQ;


import graduationProject.IoTSystem.deviceConnector.dto.DeviceBasicDataDto;
import graduationProject.IoTSystem.deviceConnector.dto.DeviceStateDto;

import java.util.Map;

public interface MQEventPublisher {
    void publishDeviceStateEvent(DeviceStateDto deviceStateDto);

    void publishDeviceInfoEvent(DeviceBasicDataDto deviceBasicDataDto);
}
