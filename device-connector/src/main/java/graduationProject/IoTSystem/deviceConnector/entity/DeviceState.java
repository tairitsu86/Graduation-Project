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
    private DeviceStateType stateType;
    private DeviceDataType dataType;
    private String stateName;
    private String valueUnit;
    private List<Object> stateOptions;
}
