package graduationProject.IoTSystem.deviceConnector.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import graduationProject.IoTSystem.deviceConnector.controller.exception.DeviceIdNotExistException;
import graduationProject.IoTSystem.deviceConnector.controller.exception.DeviceStateNotFoundException;
import graduationProject.IoTSystem.deviceConnector.dto.DeviceDto;
import graduationProject.IoTSystem.deviceConnector.entity.Device;
import graduationProject.IoTSystem.deviceConnector.entity.DeviceFunction;
import graduationProject.IoTSystem.deviceConnector.entity.DeviceState;
import graduationProject.IoTSystem.deviceConnector.entity.DeviceStateHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RepositoryServiceImpl implements RepositoryService{
    private final DeviceRepository deviceRepository;
    private final DeviceStateHistoryRepository deviceStateHistoryRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void saveDeviceStateHistory(DeviceStateHistory deviceStateHistory) {
        deviceStateHistoryRepository.save(deviceStateHistory);
    }

    @Override
    public void saveDevice(DeviceDto deviceDto) {
        String states, functions;
        try {
            states = objectMapper.writeValueAsString(deviceDto.getStates());
            functions = objectMapper.writeValueAsString(deviceDto.getFunctions());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        deviceRepository.save(
                Device.builder()
                        .deviceId(deviceDto.getDeviceId())
                        .states(states)
                        .functions(functions)
                        .build()
        );
    }

    @Override
    public DeviceDto getDevice(String deviceId) {
        if(!deviceRepository.existsById(deviceId)) throw new DeviceIdNotExistException(deviceId);
        Device device = deviceRepository.getReferenceById(deviceId);
        List<DeviceState> states;
        List<DeviceFunction> functions;
        try {
            states = objectMapper.readValue(device.getStates(), new TypeReference<>() {});
            functions = objectMapper.readValue(device.getFunctions(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return DeviceDto.builder()
                .deviceId(deviceId)
                .states(states)
                .functions(functions)
                .build();
    }
}
