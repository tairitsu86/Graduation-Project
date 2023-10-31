package graduationProject.IoTSystem.deviceConnector.repository;

import graduationProject.IoTSystem.deviceConnector.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, String> {
}
