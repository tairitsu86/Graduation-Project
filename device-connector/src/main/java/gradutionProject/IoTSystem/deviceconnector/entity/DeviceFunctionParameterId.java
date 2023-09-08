package gradutionProject.IoTSystem.deviceconnector.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class DeviceFunctionParameterId implements Serializable {
    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "function_id")
    private int functionId;

    @Column(name = "parameter_name")
    private String parameterName;

}
