package gradutionProject.IoTSystem.deviceconnector.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DeviceStateDto {
    private String deviceId;
    private String deviceName;
    private String stateName;
    private String stateValue;
}
