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
@Table(name = "device_function_parameters")
public class DeviceFunctionParameter {
    @EmbeddedId
    private DeviceFunctionParameterId deviceFunctionParameterId;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "device_id", referencedColumnName = "device_id",insertable=false, updatable=false),
            @JoinColumn(name = "function_id", referencedColumnName = "function_id",insertable=false, updatable=false)
    })
    private DeviceFunction deviceFunction;

    @Column(name = "parameter_range")
    private String parameterRange;
}
