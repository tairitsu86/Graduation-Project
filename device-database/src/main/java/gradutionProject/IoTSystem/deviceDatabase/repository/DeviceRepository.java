package gradutionProject.IoTSystem.deviceDatabase.repository;

import gradutionProject.IoTSystem.deviceDatabase.dto.GetDeviceDto;
import gradutionProject.IoTSystem.deviceDatabase.dto.GetDevicesDto;
import gradutionProject.IoTSystem.deviceDatabase.entity.Device;
import gradutionProject.IoTSystem.deviceDatabase.entity.FunctionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device,String> {
    @Query(value = "SELECT [device_id],[device_name] FROM [devices] WHERE [device_owner] = '?1'",nativeQuery = true)
    List<GetDevicesDto> getDeviceByUsername(String username);
    @Query(value = "SELECT * FROM [devices] WHERE [device_id] = '?1'",nativeQuery = true)
    Optional<GetDeviceDto> getDeviceByDeviceId(String deviceId);
    @Query(value = "SELECT [device_owner] FROM [devices] WHERE [device_id] = '?1'",nativeQuery = true)
    Optional<String> getDeviceOwner(String deviceId);
    @Query(value = "SELECT [function_type] FROM [device_functions] WHERE [device_id] = '?1' AND [function_id] = ?2",nativeQuery = true)
    Optional<FunctionType> getFunctionType(String deviceId,int functionId);

}
