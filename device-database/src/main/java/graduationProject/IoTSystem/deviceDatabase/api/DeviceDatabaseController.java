package graduationProject.IoTSystem.deviceDatabase.api;

import graduationProject.IoTSystem.deviceDatabase.api.exception.NoPermissionException;
import graduationProject.IoTSystem.deviceDatabase.dto.DeviceDto;
import graduationProject.IoTSystem.deviceDatabase.dto.DevicePermissionDto;
import graduationProject.IoTSystem.deviceDatabase.entity.Device;
import graduationProject.IoTSystem.deviceDatabase.entity.DeviceFunction;
import graduationProject.IoTSystem.deviceDatabase.rabbitMQ.MQEventPublisher;
import graduationProject.IoTSystem.deviceDatabase.repository.RepositoryService;
import graduationProject.IoTSystem.deviceDatabase.request.RestRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DeviceDatabaseController {
    private final RepositoryService repositoryService;
    private final RestRequestService restRequestService;
    private final MQEventPublisher mqEventPublisher;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String home(){
        return "This is Device Database!";
    }

    @GetMapping("/users/{username}/devices")
    @ResponseStatus(HttpStatus.OK)
    public Set<Map<String,String>> getDeviceByUsername(@PathVariable String username){
        return repositoryService.getDeviceByUsername(username);
    }
    @GetMapping("/devices/{deviceId}")
    @ResponseStatus(HttpStatus.OK)
    public DeviceDto getDevice(@PathVariable String deviceId, @RequestParam String executor){
        Device device = repositoryService.getDevice(deviceId);
        List<String> allowedUserActions = new ArrayList<>();
        if(repositoryService.checkPermission(executor, deviceId)){
            allowedUserActions.add("states");
            allowedUserActions.add("functions");
        }
        if(repositoryService.isOwner(deviceId,executor))
            allowedUserActions.add("permissions");

        return DeviceDto.builder()
                .deviceId(device.getDeviceId())
                .deviceName(device.getDeviceName())
                .description(device.getDescription())
                .owner(device.getOwner())
                .allowedUserActions(allowedUserActions)
                .build();
    }

    @GetMapping("/devices/{deviceId}/functions")
    @ResponseStatus(HttpStatus.OK)
    public List<DeviceFunction> getDeviceFunctions(@PathVariable String deviceId, @RequestParam String executor){
        if(!repositoryService.checkPermission(executor, deviceId)) throw new NoPermissionException(executor, deviceId);
        return restRequestService.getDeviceFunctions(deviceId);
    }

    @GetMapping("/devices/{deviceId}/states")
    @ResponseStatus(HttpStatus.OK)
    public Object getDeviceStates(@PathVariable String deviceId, @RequestParam String executor){
        if(!repositoryService.checkPermission(executor, deviceId)) throw new NoPermissionException(executor, deviceId);
        return restRequestService.getDeviceStates(deviceId);
    }

    @GetMapping("/devices/{deviceId}/states/{stateId}")
    @ResponseStatus(HttpStatus.OK)
    public Object getDeviceStates(@PathVariable String deviceId, @PathVariable int stateId, @RequestParam String executor){
        if(!repositoryService.checkPermission(executor, deviceId)) throw new NoPermissionException(executor, deviceId);
        return restRequestService.getDeviceStateData(deviceId, stateId);
    }

    @PutMapping("/devices/{deviceId}/permissions/grant/group")
    @ResponseStatus(HttpStatus.OK)
    public void grantPermissionToGroup(@PathVariable String deviceId, @RequestParam String executor, @RequestParam String groupId){
        if(!repositoryService.isOwner(deviceId,executor)) throw new NoPermissionException(executor, deviceId);
        repositoryService.grantPermissionToGroup(deviceId,groupId);
    }
    @PutMapping("/devices/{deviceId}/permissions/remove/group")
    @ResponseStatus(HttpStatus.OK)
    public void removePermissionFromGroup(@PathVariable String deviceId, @RequestParam String executor, @RequestParam String groupId){
        if(!repositoryService.isOwner(deviceId,executor)) throw new NoPermissionException(executor, deviceId);
        repositoryService.removePermissionFromGroup(deviceId,groupId);
    }

    @GetMapping("/devices/{deviceId}/permissions/list")
    @ResponseStatus(HttpStatus.OK)
    public DevicePermissionDto getDevicePermissionList(@PathVariable String deviceId){
        Device device = repositoryService.getDevice(deviceId);
        return DevicePermissionDto.builder()
                .users(device.getUsers())
                .groups(device.getGroups())
                .build();
    }



}











