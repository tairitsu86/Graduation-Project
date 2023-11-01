package graduationProject.IoTSystem.deviceConnector.repository;

import graduationProject.IoTSystem.deviceConnector.dto.DeviceDto;
import graduationProject.IoTSystem.deviceConnector.dto.DeviceStateDto;
import graduationProject.IoTSystem.deviceConnector.entity.DeviceFunction;
import graduationProject.IoTSystem.deviceConnector.entity.DeviceState;
import graduationProject.IoTSystem.deviceConnector.entity.DeviceStateHistory;
import graduationProject.IoTSystem.deviceConnector.entity.DeviceStateType;

import java.util.List;

public interface RepositoryService {
    void saveDeviceStateHistory(DeviceStateHistory deviceStateHistory);

    void saveDevice(DeviceDto deviceDto);

    DeviceDto getDevice(String deviceId);

    DeviceStateDto getDeviceStateDto(DeviceStateHistory deviceStateHistory);

    List<DeviceState> getDeviceStates(String deviceId);

    DeviceStateDto getDeviceStateData(String deviceId, int stateId);

    DeviceStateType getDeviceStateType(String deviceId, int stateId);

    List<DeviceFunction> getDeviceFunctions(String deviceId);

}
