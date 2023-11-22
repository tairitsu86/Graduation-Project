package graduationProject.IoTSystem.deviceConnector.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DeviceControlDto {
    private String deviceId;
    private String executor;
    private int functionId;
    private Object parameter;
}
