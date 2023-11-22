package graduationProject.IoTSystem.deviceConnector.dto;

import graduationProject.IoTSystem.deviceConnector.entity.DeviceFunction;
import graduationProject.IoTSystem.deviceConnector.entity.DeviceState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DeviceDto {
    private String deviceId;
    private List<DeviceState> states;
    private List<DeviceFunction> functions;
}