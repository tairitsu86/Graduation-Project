package graduationProject.IoTSystem.deviceConnector.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceStateHistoryId implements Serializable {
    private String deviceId;
    private String stateName;
    private Timestamp alterTime;
}
