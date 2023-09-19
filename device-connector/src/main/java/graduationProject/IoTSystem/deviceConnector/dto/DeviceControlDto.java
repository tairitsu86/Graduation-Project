package graduationProject.IoTSystem.deviceConnector.dto;

import graduationProject.IoTSystem.deviceConnector.dto.info.FunctionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DeviceControlDto {
    private String executor;
    private String deviceId;
    private int functionId;
    private FunctionType functionType;
    private String functionPara;
}
