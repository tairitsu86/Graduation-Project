package gradutionProject.IoTSystem.deviceconnector.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceInfo {
    private String deviceId;
    private String deviceName;
    private String deviceOwner;
    private List<DeviceFunction> functions;
}
