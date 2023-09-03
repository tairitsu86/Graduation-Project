package gradutionProject.IoTSystem.deviceDatabase.api;

import gradutionProject.IoTSystem.deviceDatabase.repository.DeviceRepositoryService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class DeviceDatabaseController {
    private final DeviceRepositoryService deviceRepositoryService;
    @GetMapping
    public String home(){
        return "This is Device Database!";
    }

    @GetMapping("/users/{username}/devices")
    public void getDevices(@PathVariable String username){

    }
    @GetMapping("/users/{username}/devices/{deviceId}")
    public void getDevice(@PathVariable String username,@PathVariable String deviceId){

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











