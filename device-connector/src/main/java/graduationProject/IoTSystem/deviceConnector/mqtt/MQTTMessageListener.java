package graduationProject.IoTSystem.deviceConnector.mqtt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import graduationProject.IoTSystem.deviceConnector.dto.DeviceInfoDto;
import graduationProject.IoTSystem.deviceConnector.dto.DeviceStateDto;
import graduationProject.IoTSystem.deviceConnector.entity.DeviceState;
import graduationProject.IoTSystem.deviceConnector.entity.DeviceStateId;
import graduationProject.IoTSystem.deviceConnector.repository.DeviceStateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static graduationProject.IoTSystem.deviceConnector.mqtt.MQTTConfig.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class MQTTMessageListener implements MessageHandler {
    private final ObjectMapper objectMapper;
    private final DeviceStateRepository deviceStateRepository;

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        log.info("mqtt header: {}", message.getHeaders());
        log.info("mqtt reply: {}", message.getPayload());
        String payload = message.getPayload().toString();
        switch (message.getHeaders().get("mqtt_receivedTopic").toString()){
            case TEST_TOPIC -> log.info("test topic!");
            case INFO_TOPIC -> {
//                objectMapper.readValue();
                //TODO send to device database
            }
            case STATE_TOPIC-> {
                DeviceStateDto deviceStateDto;
                try {
                    deviceStateDto = objectMapper.readValue(payload,DeviceStateDto.class);
                } catch (JsonProcessingException e) {
                    log.info("Error mapping with :{}",payload);
                    return;
                }
                deviceStateRepository.save(DeviceState.mapByDto(deviceStateDto));
            }
        }
    }
}
