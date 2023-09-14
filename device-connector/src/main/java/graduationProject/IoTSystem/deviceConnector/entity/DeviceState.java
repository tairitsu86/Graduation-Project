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
@Table(name = "device_states")
public class DeviceState {
    @EmbeddedId
    private DeviceStateId deviceStateId;

    @Column(name = "state_value")
    private String stateValue;

    public static DeviceState mapByDto(DeviceStateDto dto){
        return DeviceState.builder()
                .deviceStateId(
                        DeviceStateId.builder()
                                .deviceId(dto.getDeviceId())
                                .stateName(dto.getStateName())
                                .build()
                )
                .stateValue(dto.getStateValue())
                .build();
    }
}
