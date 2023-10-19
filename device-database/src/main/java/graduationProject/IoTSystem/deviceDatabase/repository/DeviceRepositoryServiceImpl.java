package graduationProject.IoTSystem.deviceDatabase.repository;

import graduationProject.IoTSystem.deviceDatabase.dto.GetDeviceDto;
import graduationProject.IoTSystem.deviceDatabase.dto.GetDevicesDto;
import graduationProject.IoTSystem.deviceDatabase.entity.Device;
import graduationProject.IoTSystem.deviceDatabase.entity.FunctionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceRepositoryServiceImpl implements DeviceRepositoryService{
    private final DeviceRepository deviceRepository;
    @Override
    public List<GetDevicesDto> getDevices(String username) {
        return deviceRepository.getDeviceByUsername(username);
    }

    @Override
    public GetDeviceDto getDevice(String deviceId) {
        GetDeviceDto getDeviceDto = deviceRepository.getDeviceByDeviceId(deviceId).isEmpty()?null:deviceRepository.getDeviceByDeviceId(deviceId).get();
        return getDeviceDto;
    }

    @Override
    public void updateDeviceInfo(Device device) {
        deviceRepository.save(device);
    }

    @Override
    public boolean checkPermission(String username, String deviceId) {
        if(deviceRepository.getDeviceOwner(deviceId).isEmpty()) return false;
        if(deviceRepository.getDeviceOwner(deviceId).get().equals(username)) return true;
        return false;
    }

    @Override
    public FunctionType getFunctionType(String deviceId, int functionId) {
        return deviceRepository.getFunctionType(deviceId,functionId).isEmpty()?null:deviceRepository.getFunctionType(deviceId,functionId).get();
    }
}
