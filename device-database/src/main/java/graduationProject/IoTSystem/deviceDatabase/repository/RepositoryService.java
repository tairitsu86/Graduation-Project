package graduationProject.IoTSystem.deviceDatabase.repository;

import graduationProject.IoTSystem.deviceDatabase.entity.Device;

import java.util.Map;
import java.util.Set;

public interface RepositoryService {
    Set<Map<String,String>> getDeviceByUsername(String username);
    Set<String> getDeviceIdByUsername(String username);
    void updateDeviceInfo(Device deviceDto);
    boolean isOwner(String deviceId, String username);
    void setDevice(Device deviceDto);
    Device getDevice(String deviceId);
    void grantPermissionToGroup(String deviceId, String groupId);
    void grantPermissionToUser(String deviceId, String username);
    void removePermissionFromGroup(String deviceId, String groupId);
    void removePermissionFromUser(String deviceId, String username);
    boolean checkPermission(String username,String deviceId);
}
