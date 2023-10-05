package graduationProject.IoTSystem.deviceConnector.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceStateId  implements Serializable {
    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "state_name")
    private String stateName;
}
