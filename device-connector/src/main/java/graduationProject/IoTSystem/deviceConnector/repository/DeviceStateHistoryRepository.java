package graduationProject.IoTSystem.deviceConnector.repository;

import graduationProject.IoTSystem.deviceConnector.entity.DeviceStateHistory;
import graduationProject.IoTSystem.deviceConnector.entity.DeviceStateHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DeviceStateHistoryRepository extends JpaRepository<DeviceStateHistory, DeviceStateHistoryId> {

//    @Query(name = "SELECT d2.[state_name] AS stateName, d2.[state_value] AS stateValue, d2.[executor]\n" +
//            "FROM device_state_history AS d2\n" +
//            "JOIN (\n" +
//            "\tSELECT DISTINCT [device_id], [state_name], MAX([alter_time]) AS max_time\n" +
//            "\tFROM device_state_history\n" +
//            "\tWHERE device_id = ?1\n" +
//            "\tGROUP BY [device_id], [state_name]\n" +
//            ") AS d1\n" +
//            "ON d1.device_id = d2.device_id AND d1.state_name = d2.state_name AND d1.max_time = d2.alter_time"
//            ,nativeQuery = true
//    )
//    Map<String,String> getDeviceAllStates(String deviceId);
//    List<Object> getDeviceAllStates(String deviceId);



}
