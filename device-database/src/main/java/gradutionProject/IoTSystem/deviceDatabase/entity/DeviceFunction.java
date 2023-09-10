package gradutionProject.IoTSystem.deviceDatabase.entity;

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
@Table(name = "device_functions")
public class DeviceFunction {

    @EmbeddedId
    private DeviceFunctionId deviceFunctionId;

    @ManyToOne
    @JoinColumn(name = "device_id", insertable=false, updatable=false)
    private Device device;

    @Column(name = "function_name")
    private String functionName;

    @Column(name = "function_type")
    @Enumerated(EnumType.STRING)
    private FunctionType functionType;

    @OneToMany(mappedBy = "deviceFunction")
    private List<DeviceFunctionParameter> parameters;
}
