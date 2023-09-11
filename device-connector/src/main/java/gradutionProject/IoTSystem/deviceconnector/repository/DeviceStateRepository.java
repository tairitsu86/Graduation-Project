package gradutionProject.IoTSystem.deviceconnector.repository;

import gradutionProject.IoTSystem.deviceconnector.dto.DeviceFunction;
import gradutionProject.IoTSystem.deviceconnector.entity.DeviceState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceStateRepository extends JpaRepository<DeviceState,String> {
    @Query(value = "SELECT * FROM device_functions WHERE device_id = '?1'",nativeQuery = true)
    List<DeviceFunction> getFunctions(String device_id);

    @Query(value = "SELECT function_name FROM device_functions WHERE device_id = '?1'",nativeQuery = true)
    List<String> getFunctionNames(String device_id);
}
