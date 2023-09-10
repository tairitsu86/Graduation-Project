package gradutionProject.IoTSystem.deviceconnector.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "device_info")
public class DeviceInfo {
    @Id
    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "device_name")
    private String deviceName;

    @Column(name = "device_owner")
    private String deviceOwner;

    @OneToMany(mappedBy = "deviceInfo", cascade = CascadeType.ALL)
    private List<DeviceFunction> functions;

    @ElementCollection
    @CollectionTable(name = "device_states", joinColumns = @JoinColumn(name = "device_id"))
    @MapKeyColumn(name = "state_name")
    @Column(name = "state_value")
    private Map<String, String> deviceState;
}
