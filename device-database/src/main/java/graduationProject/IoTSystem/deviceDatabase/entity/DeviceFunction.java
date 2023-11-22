package graduationProject.IoTSystem.deviceDatabase.entity;

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
    private String parameterName;
    private String valueUnit;
    private DeviceDataType dataType;
    private List<Object> functionOptions;
}
