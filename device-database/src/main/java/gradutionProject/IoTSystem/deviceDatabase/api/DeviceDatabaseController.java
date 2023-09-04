package gradutionProject.IoTSystem.deviceDatabase.api;

import gradutionProject.IoTSystem.deviceDatabase.dto.GetDeviceDto;
import gradutionProject.IoTSystem.deviceDatabase.dto.GetDevicesDto;
import gradutionProject.IoTSystem.deviceDatabase.repository.DeviceRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        //TODO add state and functions
        return deviceRepositoryService.getDevice(username,deviceId);
    }
    @PostMapping("/users/{username}/devices/new")
    public void newDevice(@PathVariable String username){

    }
    @PatchMapping("/users/{username}/devices/{deviceId}/alter")
    public void alterDevice(@PathVariable String username,@PathVariable String deviceId){

    }
    @DeleteMapping("/users/{username}/devices/{deviceId}/delete")
    public void deleteDevice(@PathVariable String username,@PathVariable String deviceId){

    }













}











