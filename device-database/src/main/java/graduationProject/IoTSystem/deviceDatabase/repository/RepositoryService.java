package graduationProject.IoTSystem.deviceDatabase.repository;

import graduationProject.IoTSystem.deviceDatabase.dto.DeviceDto;
import graduationProject.IoTSystem.deviceDatabase.dto.GetDeviceDetailDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RepositoryService {
    Set<Map<String,String>> getDeviceByUsername(String username);
    GetDeviceDetailDto getDeviceDetail(String deviceId, String username);
    void updateDeviceInfo(DeviceDto deviceDto);
    boolean isOwner(String deviceId, String username);
    void setDevice(DeviceDto deviceDto);
    DeviceDto getDevice(String deviceId);
    void grantPermissionToGroup(String deviceId, String groupId);
    void grantPermissionToUser(String deviceId, String username);
    void removePermissionFromGroup(String deviceId, String groupId);
    void removePermissionFromUser(String deviceId, String username);
    boolean checkPermission(String username,String deviceId);
}
