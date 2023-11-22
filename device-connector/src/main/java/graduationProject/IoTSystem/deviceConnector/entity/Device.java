package graduationProject.IoTSystem.deviceConnector.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "device_data")
public class Device {
    @Id
    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "states")
    private String states;

    @Column(name = "functions")
    private String functions;


}
