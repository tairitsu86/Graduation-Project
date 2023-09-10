package gradutionProject.IoTSystem.deviceconnector.dto;

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
    private int functionId;
    private String functionPara;
}
