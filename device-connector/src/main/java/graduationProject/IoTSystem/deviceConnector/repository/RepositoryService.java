package graduationProject.IoTSystem.deviceConnector.repository;


import graduationProject.IoTSystem.deviceConnector.dto.DeviceDto;
import graduationProject.IoTSystem.deviceConnector.entity.DeviceStateHistory;

public interface RepositoryService {
    void saveDeviceStateHistory(DeviceStateHistory deviceStateHistory);

    void saveDevice(DeviceDto deviceDto);

    DeviceDto getDevice(String deviceId);

}
