package graduationProject.IoTSystem.deviceDatabase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserControlDto {
    private String username;
    private String deviceId;
    private int functionId;
    private Map<String,String> parameters;
}
