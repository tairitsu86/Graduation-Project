package graduationProject.IoTSystem.deviceDatabase.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DeviceDto {
    private String deviceId;
    private String deviceName;
    private String description;
    private String owner;
    private List<String> allowedUserActions;
}
