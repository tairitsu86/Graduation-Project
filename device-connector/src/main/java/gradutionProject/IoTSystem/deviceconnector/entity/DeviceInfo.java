package gradutionProject.IoTSystem.deviceconnector.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    @Column(name = "devices_owner")
    private String devicesOwner;
    @OneToMany(mappedBy = "deviceInfo", cascade = CascadeType.ALL)
    private List<DeviceFunction> functions;
}
