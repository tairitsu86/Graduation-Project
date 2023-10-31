package graduationProject.IoTSystem.deviceConnector.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DeviceFunction {
    private int functionId;
    private String functionName;
    private DeviceDataType dataType;
    private List<Object> functionOptions;
}
