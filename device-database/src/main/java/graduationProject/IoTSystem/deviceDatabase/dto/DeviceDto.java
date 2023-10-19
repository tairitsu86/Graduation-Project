package graduationProject.IoTSystem.deviceDatabase.dto;

import graduationProject.IoTSystem.deviceDatabase.entity.DeviceFunction;
import graduationProject.IoTSystem.deviceDatabase.entity.DeviceState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDto {
    private String deviceId;
    private String deviceName;
    private String description;
    private String owner;
    private List<DeviceState> states;
    private List<DeviceFunction> functions;
    private Set<String> groups;
    private Set<String> users;
}
