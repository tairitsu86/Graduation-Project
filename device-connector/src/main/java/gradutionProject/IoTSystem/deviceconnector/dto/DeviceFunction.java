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
public class DeviceFunction {
    private int functionId;
    private String functionName;
    private FunctionType functionType;
    private List<DeviceFunctionParameter> parameters;
}
