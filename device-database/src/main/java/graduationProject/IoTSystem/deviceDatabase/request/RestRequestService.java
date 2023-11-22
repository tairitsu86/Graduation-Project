package graduationProject.IoTSystem.deviceDatabase.request;

import graduationProject.IoTSystem.deviceDatabase.entity.DeviceFunction;

import java.util.List;

public interface RestRequestService {
    List<String> getGroupsByUsername(String username);
    List<DeviceFunction> getDeviceFunctions(String deviceId);
    Object getDeviceStates(String deviceId);
    Object getDeviceStateData(String deviceId, int stateId);

}
