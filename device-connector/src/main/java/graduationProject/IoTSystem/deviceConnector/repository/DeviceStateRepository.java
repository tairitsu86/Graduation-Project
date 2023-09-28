package graduationProject.IoTSystem.deviceConnector.repository;

import graduationProject.IoTSystem.deviceConnector.dto.DeviceStateDto;
import graduationProject.IoTSystem.deviceConnector.entity.DeviceState;
import graduationProject.IoTSystem.deviceConnector.entity.DeviceStateId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceStateRepository extends JpaRepository<DeviceState, DeviceStateId> {
    @Query(value = "SELECT [state_name],[state_value] FROM [device_states] WHERE [device_id] = '?1'",nativeQuery = true)
    List<DeviceStateDto> getState(String deviceId);

    @Query(value = "SELECT [state_name],[state_value] FROM [device_states] WHERE [device_id] = '?1' AND [state_name] = '?2'",nativeQuery = true)
    List<DeviceStateDto> getState(String deviceId,String stateName);

}