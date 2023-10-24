package graduationProject.IoTSystem.deviceConnector.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DeviceStateDto {
    private String notificationType;
    private String from;
    private String executor;
    private String deviceId;
    private String deviceName;
    private String stateName;
    private String stateValue;
}
