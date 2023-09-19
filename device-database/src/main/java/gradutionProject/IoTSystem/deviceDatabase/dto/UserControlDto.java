package gradutionProject.IoTSystem.deviceDatabase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserControlDto {
    private String username;
    private String deviceId;
    private int functionId;
    private String functionPara;
}
