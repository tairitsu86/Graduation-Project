package graduationProject.IoTSystem.deviceConnector.repository;

import graduationProject.IoTSystem.deviceConnector.entity.DeviceStateHistory;
import graduationProject.IoTSystem.deviceConnector.entity.DeviceStateHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceStateHistoryRepository extends JpaRepository<DeviceStateHistory, DeviceStateHistoryId> {

}
