package graduationProject.IoTSystem.deviceConnector.mqtt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import graduationProject.IoTSystem.deviceConnector.dto.DeviceBasicDataDto;
import graduationProject.IoTSystem.deviceConnector.dto.DeviceDto;
import graduationProject.IoTSystem.deviceConnector.dto.DeviceInfoDto;
import graduationProject.IoTSystem.deviceConnector.dto.DeviceStateDto;
import graduationProject.IoTSystem.deviceConnector.entity.Device;
import graduationProject.IoTSystem.deviceConnector.entity.DeviceStateHistory;
import graduationProject.IoTSystem.deviceConnector.entity.DeviceStateType;
import graduationProject.IoTSystem.deviceConnector.rabbitMQ.MQEventPublisher;
import graduationProject.IoTSystem.deviceConnector.repository.DeviceStateHistoryRepository;
import graduationProject.IoTSystem.deviceConnector.repository.RepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static graduationProject.IoTSystem.deviceConnector.mqtt.MQTTConfig.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class MQTTListener implements MessageHandler {
    private final ObjectMapper objectMapper;
    private final RepositoryService repositoryService;
    private final MQEventPublisher mqEventPublisher;

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        log.info("mqtt header: {}", message.getHeaders());
        log.info("mqtt reply: {}", message.getPayload());
        String payload = message.getPayload().toString();
        switch (Objects.requireNonNull(message.getHeaders().get("mqtt_receivedTopic"),"Mqtt topic is null").toString()){
            case TEST_TOPIC -> log.info("test topic!");
            case INFO_TOPIC -> {
                DeviceInfoDto deviceInfoDto;
                try {
                    deviceInfoDto = objectMapper.readValue(payload, new TypeReference<>() {});
                } catch (JsonProcessingException e) {
                    log.info("Error mapping with :{}",payload);
                    return;
                }

                mqEventPublisher.publishDeviceInfoEvent(
                        DeviceBasicDataDto.builder()
                                .deviceId(deviceInfoDto.getDeviceId())
                                .deviceName(deviceInfoDto.getDeviceName())
                                .description(deviceInfoDto.getDescription())
                                .owner(deviceInfoDto.getOwner())
                                .build()
                );
                repositoryService.saveDevice(
                        DeviceDto.builder()
                                .deviceId(deviceInfoDto.getDeviceId())
                                .states(deviceInfoDto.getStates())
                                .functions(deviceInfoDto.getFunctions())
                                .build()
                );
            }
            case STATE_TOPIC-> {
                DeviceStateHistory deviceStateHistory;
                try {
                    deviceStateHistory = objectMapper.readValue(payload, DeviceStateHistory.class);
                } catch (JsonProcessingException e) {
                    log.info("Error mapping with :{}, and exception:{}",payload,e.getMessage());
                    return;
                }
                if(repositoryService.getDeviceStateType(deviceStateHistory.getDeviceId(),deviceStateHistory.getStateId()).equals(DeviceStateType.PASSIVE))
                    mqEventPublisher.publishDeviceStateEvent(repositoryService.getDeviceStateDto(deviceStateHistory));
                repositoryService.saveDeviceStateHistory(deviceStateHistory);
            }
        }
    }
}
