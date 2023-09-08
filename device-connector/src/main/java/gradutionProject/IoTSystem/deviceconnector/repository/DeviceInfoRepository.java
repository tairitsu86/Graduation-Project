package gradutionProject.IoTSystem.deviceconnector.repository;

import gradutionProject.IoTSystem.deviceconnector.entity.DeviceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceInfoRepository extends JpaRepository<DeviceInfo,String> {
}
