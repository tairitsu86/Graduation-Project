package graduationProject.IoTSystem.deviceConnector.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class DeviceStateId {
    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "state_name")
    private String stateName;
}
