package gradutionProject.IoTSystem.deviceDatabase.api;

import gradutionProject.IoTSystem.deviceDatabase.dto.GetDeviceDto;
import gradutionProject.IoTSystem.deviceDatabase.dto.GetDevicesDto;
import gradutionProject.IoTSystem.deviceDatabase.repository.DeviceRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DeviceDatabaseController {
    private final DeviceRepositoryService deviceRepositoryService;
    @GetMapping
    public String home(){
        return "This is Device Database!";
    }

    @GetMapping("/users/{username}/devices")
    public List<GetDevicesDto> getDevices(@PathVariable String username){
        return deviceRepositoryService.getDevices(username);
    }
    @GetMapping("/users/{username}/devices/{deviceId}")
    public GetDeviceDto getDevice(@PathVariable String username, @PathVariable String deviceId){
        return deviceRepositoryService.getDevice(username,deviceId);
    }
    @GetMapping("/devices/new/id")
    public String newDeviceId(){
        return UUID.randomUUID().toString();
    }















}











