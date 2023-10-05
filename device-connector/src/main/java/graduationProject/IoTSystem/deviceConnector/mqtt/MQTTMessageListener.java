package graduationProject.IoTSystem.deviceConnector.mqtt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import graduationProject.IoTSystem.deviceConnector.dto.DeviceInfoDto;
import graduationProject.IoTSystem.deviceConnector.dto.DeviceStateDto;
import graduationProject.IoTSystem.deviceConnector.dto.info.FunctionType;
import graduationProject.IoTSystem.deviceConnector.entity.DeviceStateHistory;
import graduationProject.IoTSystem.deviceConnector.rabbitMQ.MQEventPublisher;
import graduationProject.IoTSystem.deviceConnector.repository.DeviceStateHistoryRepository;
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
public class MQTTMessageListener implements MessageHandler {
    private final ObjectMapper objectMapper;
    private final DeviceStateHistoryRepository deviceStateHistoryRepository;
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
                    deviceInfoDto = objectMapper.readValue(payload,DeviceInfoDto.class);
                } catch (JsonProcessingException e) {
                    log.info("Error mapping with :{}",payload);
                    return;
                }
                mqEventPublisher.publishDeviceInfoEvent(deviceInfoDto);
            }
            case STATE_TOPIC-> {
                DeviceStateDto deviceStateDto;
                try {
                    deviceStateDto = objectMapper.readValue(payload,DeviceStateDto.class);
                } catch (JsonProcessingException e) {
                    log.info("Error mapping with :{}, and exception:{}",payload,e.getMessage());
                    return;
                }
                mqEventPublisher.publishDeviceStateEvent(deviceStateDto);
                if(deviceStateDto.getCallBy() == FunctionType.CONTROL)
                    deviceStateHistoryRepository.save(DeviceStateHistory.mapByDto(deviceStateDto));
            }
        }
    }
}
