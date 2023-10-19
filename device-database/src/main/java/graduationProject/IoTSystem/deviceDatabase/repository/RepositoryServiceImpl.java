package graduationProject.IoTSystem.deviceDatabase.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import graduationProject.IoTSystem.deviceDatabase.api.exception.DeviceNotExistException;
import graduationProject.IoTSystem.deviceDatabase.dto.DeviceDto;
import graduationProject.IoTSystem.deviceDatabase.dto.GetDeviceDetailDto;
import graduationProject.IoTSystem.deviceDatabase.entity.Device;
import graduationProject.IoTSystem.deviceDatabase.entity.DeviceFunction;
import graduationProject.IoTSystem.deviceDatabase.entity.DeviceFunctionParameter;
import graduationProject.IoTSystem.deviceDatabase.entity.DeviceState;
import graduationProject.IoTSystem.deviceDatabase.request.RestRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RepositoryServiceImpl implements RepositoryService {
    private final DeviceRepository deviceRepository;
    private final RestRequestService restRequestService;
    private final ObjectMapper objectMapper;
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
        if(userResult!=null)
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
    public GetDeviceDetailDto getDeviceDetail(String deviceId, String username) {
        DeviceDto deviceDto = getDevice(deviceId);
        GetDeviceDetailDto getDeviceDetailDto = GetDeviceDetailDto.builder()
                .deviceId(deviceDto.getDeviceId())
                .deviceName(deviceDto.getDeviceName())
                .owner(deviceDto.getOwner())
                .description(deviceDto.getDescription())
                .functions(deviceDto.getFunctions())
                .build();
        if(isOwner(deviceId,username))
            addPermissionFunction(getDeviceDetailDto.getFunctions());
        return getDeviceDetailDto;
    }


    @Override
    public void updateDeviceInfo(DeviceDto deviceDto) {
        Device device = Device.builder()
                .deviceId(deviceDto.getDeviceId())
                .deviceName(deviceDto.getDeviceName())
                .description(deviceDto.getDescription())
                .owner(deviceDto.getOwner())
                .build();
        String states,functions;
        try{
            states = objectMapper.writeValueAsString(deviceDto.getStates());
            functions = objectMapper.writeValueAsString(deviceDto.getFunctions());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("updateDeviceInfo mapping error with\n"+deviceDto);
        }
        device.setStates(states);
        device.setFunctions(functions);

        if(deviceRepository.existsById(device.getDeviceId())){
            Device oldDevice = deviceRepository.getReferenceById(device.getDeviceId());
            device.setGroups(oldDevice.getGroups());
            device.setUsers(oldDevice.getUsers());
        }else{
            device.setUsers(new HashSet<>(){{add(deviceDto.getOwner());}});
        }
        deviceRepository.save(device);
    }

    @Override
    public boolean isOwner(String deviceId, String username) {
        if(!deviceRepository.existsById(deviceId)) return false;
        return deviceRepository.getReferenceById(deviceId).getOwner().equals(username);
    }

    @Override
    public void setDevice(DeviceDto deviceDto) {
        Device device = Device.builder()
                .deviceId(deviceDto.getDeviceId())
                .deviceName(deviceDto.getDeviceName())
                .description(deviceDto.getDescription())
                .owner(deviceDto.getOwner())
                .users(deviceDto.getUsers())
                .groups(deviceDto.getGroups())
                .build();
        String states,functions;
        try{
            states = objectMapper.writeValueAsString(deviceDto.getStates());
            functions = objectMapper.writeValueAsString(deviceDto.getFunctions());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("setDevice mapping error with\n"+deviceDto);
        }
        device.setStates(states);
        device.setFunctions(functions);

        deviceRepository.save(device);
    }

    @Override
    public DeviceDto getDevice(String deviceId) {
        if(!deviceRepository.existsById(deviceId)) throw new DeviceNotExistException(deviceId);
        Device device = deviceRepository.getReferenceById(deviceId);
        DeviceDto deviceDto = DeviceDto.builder()
                .deviceId(deviceId)
                .deviceName(device.getDeviceName())
                .description(device.getDescription())
                .owner(device.getOwner())
                .users(device.getUsers())
                .groups(device.getGroups())
                .build();

        List<DeviceState> states;
        List<DeviceFunction> functions;
        try{
            states = objectMapper.readValue(device.getStates(), new TypeReference<>(){});
            functions = objectMapper.readValue(device.getFunctions(), new TypeReference<>(){});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("getDevice mapping error with\n"+device);
        }

        deviceDto.setStates(states);
        deviceDto.setFunctions(functions);
        return deviceDto;
    }

    @Override
    public void grantPermissionToGroup(String deviceId, String groupId) {
        DeviceDto deviceDto = getDevice(deviceId);
        deviceDto.getGroups().add(groupId);
        setDevice(deviceDto);
    }

    @Override
    public void grantPermissionToUser(String deviceId, String username) {
        DeviceDto deviceDto = getDevice(deviceId);
        deviceDto.getUsers().add(username);
        setDevice(deviceDto);
    }

    @Override
    public void removePermissionFromGroup(String deviceId, String groupId) {
        DeviceDto deviceDto = getDevice(deviceId);
        deviceDto.getGroups().remove(groupId);
        setDevice(deviceDto);
    }

    @Override
    public void removePermissionFromUser(String deviceId, String username) {
        DeviceDto deviceDto = getDevice(deviceId);
        deviceDto.getUsers().remove(username);
        setDevice(deviceDto);
    }


    @Override
    public boolean checkPermission(String username, String deviceId) {
        Device device = deviceRepository.getReferenceById(deviceId);
        if(device.getUsers().contains(username)) return true;
        return false;
    }

    public void addPermissionFunction(List<DeviceFunction> functions){
        functions.add(
                DeviceFunction.builder()
                        .functionId(-1)
                        .functionName("Grant device permission to user")
                        .parameters(
                                new ArrayList<>(){{
                                    add(
                                            DeviceFunctionParameter.builder()
                                                    .parameterName("USERNAME")
                                                    .parameterRange("ANY")
                                                    .build()
                                    );
                                }}
                        )
                        .build()
        );
        functions.add(
                DeviceFunction.builder()
                        .functionId(-2)
                        .functionName("Grant device permission to group")
                        .parameters(
                                new ArrayList<>(){{
                                    add(
                                            DeviceFunctionParameter.builder()
                                                    .parameterName("GROUP_ID")
                                                    .parameterRange("ANY")
                                                    .build()
                                    );
                                }}
                        )
                        .build()
        );
        functions.add(
                DeviceFunction.builder()
                        .functionId(-3)
                        .functionName("Remove device permission from user")
                        .parameters(
                                new ArrayList<>(){{
                                    add(
                                            DeviceFunctionParameter.builder()
                                                    .parameterName("USERNAME")
                                                    .parameterRange("ANY")
                                                    .build()
                                    );
                                }}
                        )
                        .build()
        );
        functions.add(
                DeviceFunction.builder()
                        .functionId(-4)
                        .functionName("Remove device permission from group")
                        .parameters(
                                new ArrayList<>(){{
                                    add(
                                            DeviceFunctionParameter.builder()
                                                    .parameterName("GROUP_ID")
                                                    .parameterRange("ANY")
                                                    .build()
                                    );
                                }}
                        )
                        .build()
        );
    }
}
