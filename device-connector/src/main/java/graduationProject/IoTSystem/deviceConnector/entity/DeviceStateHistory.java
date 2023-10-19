package graduationProject.IoTSystem.deviceConnector.entity;

import graduationProject.IoTSystem.deviceConnector.dto.DeviceStateDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@IdClass(DeviceStateHistoryId.class)
@Table(name = "device_state_history")
public class DeviceStateHistory {
    @Id
    @Column(name = "device_id")
    private String deviceId;

    @Id
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "alter_time")
    private Timestamp alterTime;

    @Id
    @Column(name = "state_name")
    private String stateName;

    @Column(name = "state_value")
    private String stateValue;

    @Column(name = "executor")
    private String executor;

    public static DeviceStateHistory mapByDto(DeviceStateDto dto){
        return DeviceStateHistory.builder()
                .deviceId(dto.getDeviceId())
                .alterTime(Timestamp.from(Instant.now()))
                .stateName(dto.getStateName())
                .stateValue(dto.getStateValue())
                .executor(dto.getExecutor())
                .build();
    }
}
