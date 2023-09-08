package gradutionProject.IoTSystem.deviceconnector.repository;

import gradutionProject.IoTSystem.deviceconnector.entity.DeviceFunction;
import gradutionProject.IoTSystem.deviceconnector.entity.DeviceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceInfoRepository extends JpaRepository<DeviceInfo,String> {
    @Query(value = "SELECT * FROM device_functions WHERE device_id = '?1'",nativeQuery = true)
    List<DeviceFunction> getFunctions(String device_id);
}
