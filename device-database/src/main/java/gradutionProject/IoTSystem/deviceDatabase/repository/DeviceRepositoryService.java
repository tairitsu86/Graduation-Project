package gradutionProject.IoTSystem.deviceDatabase.repository;

import gradutionProject.IoTSystem.deviceDatabase.dto.GetDevicesDto;
import gradutionProject.IoTSystem.deviceDatabase.dto.GetDeviceDto;

import java.util.List;

public interface DeviceRepositoryService {
    List<GetDevicesDto> getDevices(String username);

    GetDeviceDto getDevice(String username, String deviceId);
}
