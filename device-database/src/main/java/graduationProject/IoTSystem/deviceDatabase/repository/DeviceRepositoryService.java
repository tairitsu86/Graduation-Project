package graduationProject.IoTSystem.deviceDatabase.repository;

import graduationProject.IoTSystem.deviceDatabase.dto.GetDeviceDto;
import graduationProject.IoTSystem.deviceDatabase.dto.GetDevicesDto;
import graduationProject.IoTSystem.deviceDatabase.entity.Device;
import graduationProject.IoTSystem.deviceDatabase.entity.FunctionType;

import java.util.List;

public interface DeviceRepositoryService {
    List<GetDevicesDto> getDevices(String username);
    GetDeviceDto getDevice(String deviceId);
    void updateDeviceInfo(Device device);
    boolean checkPermission(String username,String deviceId);
    FunctionType getFunctionType(String deviceId, int functionId);
}
