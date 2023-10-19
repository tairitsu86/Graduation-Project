package graduationProject.IoTSystem.deviceDatabase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetDeviceDto {
    private String deviceId;
    private String deviceName;
    private String deviceDescription;
    private String deviceOwner;
}
