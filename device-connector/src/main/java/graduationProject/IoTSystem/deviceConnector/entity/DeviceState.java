package graduationProject.IoTSystem.deviceConnector.entity;

import graduationProject.IoTSystem.deviceConnector.dto.DeviceStateDto;
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
@IdClass(DeviceStateId.class)
@Table(name = "device_states")
public class DeviceState {
    @Id
    @Column(name = "device_id")
    private String deviceId;
    @Id
    @Column(name = "state_name")
    private String stateName;

    @Column(name = "state_value")
    private String stateValue;

    public static DeviceState mapByDto(DeviceStateDto dto){
        return DeviceState.builder()
                .deviceId(dto.getDeviceId())
                .stateName(dto.getStateName())
                .stateValue(dto.getStateValue())
                .build();
    }
}
