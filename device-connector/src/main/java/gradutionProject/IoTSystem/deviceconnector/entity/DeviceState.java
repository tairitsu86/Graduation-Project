package gradutionProject.IoTSystem.deviceconnector.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "device_states")
public class DeviceState {
    @Id
    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "state_name")
    private String stateName;

    @Column(name = "state_value")
    private String stateValue;
}
