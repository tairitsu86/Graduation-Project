package graduationProject.IoTSystem.deviceDatabase.repository;

import graduationProject.IoTSystem.deviceDatabase.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DeviceRepository extends JpaRepository<Device,String> {
    @Query(value = "SELECT D.device_id AS deviceId, D.device_name AS deviceName \n" +
            "FROM [dbo].[device] AS D\n" +
            "JOIN [dbo].[device_permission_users] AS U ON D.device_id = U.device_id\n" +
            "WHERE  username = ?1",nativeQuery = true)
    List<Map<String,String>> getDeviceByUsername(String username);

    @Query(value = "SELECT D.device_id AS deviceId, D.device_name AS deviceName \n" +
            "FROM [dbo].[device] AS D\n" +
            "JOIN [dbo].[device_permission_groups] AS G ON D.device_id = G.device_id\n" +
            "WHERE group_id in (?1)",nativeQuery = true)
    List<Map<String,String>> getDeviceByGroupList(List<String> groupList);

    @Query(value = "SELECT D.device_id AS deviceId \n" +
            "FROM [dbo].[device] AS D\n" +
            "JOIN [dbo].[device_permission_users] AS U ON D.device_id = U.device_id\n" +
            "WHERE  username = ?1",nativeQuery = true)
    List<String> getDeviceIdByUsername(String username);

    @Query(value = "SELECT D.device_id AS deviceId \n" +
            "FROM [dbo].[device] AS D\n" +
            "JOIN [dbo].[device_permission_groups] AS G ON D.device_id = G.device_id\n" +
            "WHERE group_id in (?1)",nativeQuery = true)
    List<String> getDeviceIdByGroupList(List<String> groupList);
}
