package gradutionProject.IoTSystem.deviceconnector.dto.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceFunctionParameter {
    private String parameterName;
    private String parameterRange;
}
