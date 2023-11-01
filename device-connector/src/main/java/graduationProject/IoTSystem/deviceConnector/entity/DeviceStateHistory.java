package graduationProject.IoTSystem.deviceConnector.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

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
    @Column(name = "state_id")
    private int stateId;

    @Column(name = "state_value")
    private String stateValue;

    @Column(name = "executor")
    private String executor;

}
