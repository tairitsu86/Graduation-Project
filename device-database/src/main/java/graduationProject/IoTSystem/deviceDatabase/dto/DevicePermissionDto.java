package graduationProject.IoTSystem.deviceDatabase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DevicePermissionDto {
    private Set<String> users;
    private Set<String> groups;
}
