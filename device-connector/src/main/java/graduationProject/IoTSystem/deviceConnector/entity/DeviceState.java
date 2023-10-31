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
public class DeviceState {
    private int stateId;
    private DeviceDataType stateType;
    private String stateName;
    private List<String> stateOptions;
}
