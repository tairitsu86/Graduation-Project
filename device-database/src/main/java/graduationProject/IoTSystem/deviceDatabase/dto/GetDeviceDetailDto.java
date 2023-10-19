package graduationProject.IoTSystem.deviceDatabase.dto;

import graduationProject.IoTSystem.deviceDatabase.entity.DeviceFunction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetDeviceDetailDto {
    private String deviceId;
    private String deviceName;
    private String description;
    private String owner;
    private List<DeviceFunction> functions;
}
