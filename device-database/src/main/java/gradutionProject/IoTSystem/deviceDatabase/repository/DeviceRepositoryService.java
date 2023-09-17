package gradutionProject.IoTSystem.deviceDatabase.repository;

import gradutionProject.IoTSystem.deviceDatabase.dto.GetDevicesDto;
import gradutionProject.IoTSystem.deviceDatabase.dto.GetDeviceDto;
import gradutionProject.IoTSystem.deviceDatabase.entity.Device;
import gradutionProject.IoTSystem.deviceDatabase.entity.FunctionType;

import java.util.List;

public interface DeviceRepositoryService {
    List<GetDevicesDto> getDevices(String username);
    GetDeviceDto getDevice(String deviceId);
    void updateDeviceInfo(Device device);
    boolean checkPermission(String username,String deviceId);
    FunctionType getFunctionType(String deviceId,int functionId);
}
