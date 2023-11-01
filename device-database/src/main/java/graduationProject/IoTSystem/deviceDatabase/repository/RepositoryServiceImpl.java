package graduationProject.IoTSystem.deviceDatabase.repository;

import graduationProject.IoTSystem.deviceDatabase.api.exception.DeviceNotExistException;
import graduationProject.IoTSystem.deviceDatabase.entity.Device;
import graduationProject.IoTSystem.deviceDatabase.entity.DeviceDataType;
import graduationProject.IoTSystem.deviceDatabase.entity.DeviceFunction;
import graduationProject.IoTSystem.deviceDatabase.request.RestRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RepositoryServiceImpl implements RepositoryService {
    private final DeviceRepository deviceRepository;
    private final RestRequestService restRequestService;
    @Override
    public Set<Map<String,String>> getDeviceByUsername(String username) {
        Set<Map<String,String>> result = new HashSet<>();

        List<Map<String,String>> userResult = deviceRepository.getDeviceByUsername(username);
        if(userResult!=null)
            result.addAll(userResult);

        List<String> groupList = restRequestService.getGroupsByUsername(username);
        if(groupList==null||groupList.size()==0)
            return result;
        List<Map<String,String>> groupResult = deviceRepository.getDeviceByGroupList(groupList);
        if(groupResult!=null)
            outFor:
            for (Map<String,String> newData:groupResult) {
                for (Map<String,String> data:result) {
                    if(newData.get("deviceId")!=null&&newData.get("deviceId").equals(data.get("deviceId")))
                        continue outFor;
                }
                result.add(newData);
            }
        return result;
    }

    @Override
    public Set<String> getDeviceIdByUsername(String username) {
        Set<String> result = new HashSet<>();

        List<String> userResult = deviceRepository.getDeviceIdByUsername(username);
        if(userResult!=null)
            result.addAll(userResult);

        List<String> groupList = restRequestService.getGroupsByUsername(username);
        if(groupList==null||groupList.size()==0)
            return result;
        List<String> groupResult = deviceRepository.getDeviceIdByGroupList(groupList);

        if(groupResult!=null)
            result.addAll(groupResult);

        return result;
    }


    @Override
    public void updateDeviceInfo(Device device) {
        if(deviceRepository.existsById(device.getDeviceId())){
            Device oldDevice = deviceRepository.getReferenceById(device.getDeviceId());
            device.setGroups(oldDevice.getGroups());
            device.setUsers(oldDevice.getUsers());
        }else{
            device.setUsers(new HashSet<>(){{add(device.getOwner());}});
        }
        setDevice(device);
    }

    @Override
    public boolean isOwner(String deviceId, String username) {
        if(!deviceRepository.existsById(deviceId)) return false;
        return deviceRepository.getReferenceById(deviceId).getOwner().equals(username);
    }

    @Override
    public void setDevice(Device device) {
        deviceRepository.save(device);
    }

    @Override
    public Device getDevice(String deviceId) {
        if(!deviceRepository.existsById(deviceId)) throw new DeviceNotExistException(deviceId);
        return deviceRepository.getReferenceById(deviceId);
    }

    @Override
    public void grantPermissionToGroup(String deviceId, String groupId) {
        Device device = getDevice(deviceId);
        device.getGroups().add(groupId);
        setDevice(device);
    }

    @Override
    public void grantPermissionToUser(String deviceId, String username) {
        Device device = getDevice(deviceId);
        device.getUsers().add(username);
        setDevice(device);
    }

    @Override
    public void removePermissionFromGroup(String deviceId, String groupId) {
        Device device = getDevice(deviceId);
        device.getGroups().remove(groupId);
        setDevice(device);
    }

    @Override
    public void removePermissionFromUser(String deviceId, String username) {
        Device device = getDevice(deviceId);
        device.getUsers().remove(username);
        setDevice(device);
    }


    @Override
    public boolean checkPermission(String username, String deviceId) {
        return  getDeviceIdByUsername(username).contains(deviceId);
    }


}
