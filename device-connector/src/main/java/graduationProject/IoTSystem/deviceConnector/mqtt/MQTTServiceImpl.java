package graduationProject.IoTSystem.deviceConnector.mqtt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import graduationProject.IoTSystem.deviceConnector.dto.DeviceControlDto;
import graduationProject.IoTSystem.deviceConnector.dto.DeviceStateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class MQTTServiceImpl implements MQTTService{
    private final ObjectMapper objectMapper;
    private final MQTTTemplate mqttTemplate;

    @Override
    public void controlDevice(DeviceControlDto deviceControlDto){
        String payload;
        try {
            payload = objectMapper.writeValueAsString(deviceControlDto);
        } catch (JsonProcessingException e) {
            log.info("mapper to string error: {}",deviceControlDto);
            return;
        }
        mqttTemplate.publish(payload,controlTopic(deviceControlDto.getDeviceId()),1);
    }

    public String controlTopic(String deviceId){
        return String.format("devices/%s/control",deviceId);
    }



}
