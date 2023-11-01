package graduationProject.IoTSystem.deviceConnector.repository;

import graduationProject.IoTSystem.deviceConnector.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DeviceRepository extends JpaRepository<Device, String> {
    @Query("SELECT d.states FROM Device AS d WHERE d.deviceId = :deviceId")
    String getStatesByDeviceId(String deviceId);

    @Query("SELECT d.functions FROM Device AS d WHERE d.deviceId = :deviceId")
    String getFunctionsByDeviceId(String deviceId);
}
