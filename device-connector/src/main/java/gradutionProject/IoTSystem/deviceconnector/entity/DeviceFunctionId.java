package gradutionProject.IoTSystem.deviceconnector.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class DeviceFunctionId implements Serializable {
    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "function_id")
    private int functionId;

}