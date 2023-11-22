package graduationProject.IoTSystem.deviceConnector.repository;

import graduationProject.IoTSystem.deviceConnector.entity.DeviceStateHistory;
import graduationProject.IoTSystem.deviceConnector.entity.DeviceStateHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceStateHistoryRepository extends JpaRepository<DeviceStateHistory, DeviceStateHistoryId> {

    @Query(value = "SELECT TOP(1) * FROM [device_state_history]\n" +
            "WHERE [device_id] = ?1 AND [state_id] = ?2\n" +
            "ORDER BY [alter_time] DESC", nativeQuery = true)
    Optional<DeviceStateHistory> getLatestState(String deviceId, int stateId);



}
