package gradutionProject.IoTSystem.deviceDatabase.repository;

import gradutionProject.IoTSystem.deviceDatabase.dto.GetDeviceDto;
import gradutionProject.IoTSystem.deviceDatabase.dto.GetDevicesDto;
import gradutionProject.IoTSystem.deviceDatabase.entity.Device;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceRepositoryServiceImpl implements DeviceRepositoryService{
    private final DeviceRepository deviceRepository;
    //TODO
    @Override
    public List<GetDevicesDto> getDevices(String username) {
        return null;
    }

    @Override
    public GetDeviceDto getDevice(String username, String deviceId) {
        return null;
    }

    @Override
    public void updateDeviceInfo(Device device) {

    }

    @Override
    public boolean checkPermission(String username, String deviceId) {
        return false;
    }
}
