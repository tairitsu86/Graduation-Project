package gradutionProject.IoTSystem.deviceconnector.entity;

import jakarta.persistence.*;
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
    @EmbeddedId
    private DeviceStateId deviceStateId;

    @Column(name = "state_value")
    private String stateValue;
}
