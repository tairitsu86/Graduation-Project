package graduationProject.IoTSystem.deviceConnector.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NewDeviceDto {
    private String id;
    private String mqttHostIp;
    private String mqttHostPort;
}
