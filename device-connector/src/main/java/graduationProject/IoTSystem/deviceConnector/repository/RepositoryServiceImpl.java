package graduationProject.IoTSystem.deviceConnector.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import graduationProject.IoTSystem.deviceConnector.controller.exception.DeviceIdNotExistException;
import graduationProject.IoTSystem.deviceConnector.controller.exception.DeviceStateNotFoundException;
import graduationProject.IoTSystem.deviceConnector.dto.DeviceDto;
import graduationProject.IoTSystem.deviceConnector.dto.DeviceStateDto;
import graduationProject.IoTSystem.deviceConnector.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RepositoryServiceImpl implements RepositoryService{
    private final DeviceRepository deviceRepository;
    private final DeviceStateHistoryRepository deviceStateHistoryRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void saveDeviceStateHistory(DeviceStateHistory deviceStateHistory) {
        deviceStateHistory.setAlterTime(Timestamp.from(Instant.now()));
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
        return DeviceDto.builder()
                .deviceId(deviceId)
                .states(getDeviceStates(deviceId))
                .functions(getDeviceFunctions(deviceId))
                .build();
    }

    @Override
    public DeviceStateDto getDeviceStateDto(DeviceStateHistory deviceStateHistory) {
        String deviceId = deviceStateHistory.getDeviceId();
        DeviceState state = getDeviceStates(deviceId).get(deviceStateHistory.getStateId());
        String stateName, stateValue, valueTemp;
        stateName = state.getStateName();
        switch(state.getDataType()){
            case OPTIONS -> valueTemp = state.getStateOptions().get(Integer.parseInt(deviceStateHistory.getStateValue())).toString();
            default -> valueTemp = deviceStateHistory.getStateValue();
        }
        stateValue = String.format("%s%s", valueTemp, state.getValueUnit()==null?"":state.getValueUnit());
        return DeviceStateDto.builder()
                .deviceId(deviceId)
                .stateName(stateName)
                .stateValue(stateValue)
                .executor(deviceStateHistory.getExecutor())
                .build();
    }

    @Override
    public List<DeviceState> getDeviceStates(String deviceId) {
        if(!deviceRepository.existsById(deviceId)) throw new DeviceIdNotExistException(deviceId);
        String data = deviceRepository.getStatesByDeviceId(deviceId);
        List<DeviceState> states;
        try {
            states = objectMapper.readValue(data, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return states;
    }

    @Override
    public DeviceStateDto getDeviceStateData(String deviceId, int stateId) {
        Optional<DeviceStateHistory> temp = deviceStateHistoryRepository.getLatestState(deviceId, stateId);
        if(temp.isEmpty()) throw new DeviceStateNotFoundException(deviceId, stateId);
        return getDeviceStateDto(temp.get());
    }

    @Override
    public DeviceStateType getDeviceStateType(String deviceId, int stateId) {
        return getDeviceStates(deviceId).get(stateId).getStateType();
    }

    @Override
    public List<DeviceFunction> getDeviceFunctions(String deviceId) {
        if(!deviceRepository.existsById(deviceId)) throw new DeviceIdNotExistException(deviceId);
        String data = deviceRepository.getFunctionsByDeviceId(deviceId);
        List<DeviceFunction> functions;
        try {
            functions = objectMapper.readValue(data, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return functions;
    }
}
