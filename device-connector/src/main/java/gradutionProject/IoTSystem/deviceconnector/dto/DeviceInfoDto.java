package gradutionProject.IoTSystem.deviceconnector.dto;

import gradutionProject.IoTSystem.deviceconnector.dto.info.DeviceFunction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceInfoDto {
    private String deviceId;
    private String deviceName;
    private String deviceOwner;
    private List<DeviceFunction> functions;
}
