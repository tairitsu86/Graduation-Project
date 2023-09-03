package gradutionProject.IoTSystem.deviceDatabase.repository;

import gradutionProject.IoTSystem.deviceDatabase.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device,String> {
}
