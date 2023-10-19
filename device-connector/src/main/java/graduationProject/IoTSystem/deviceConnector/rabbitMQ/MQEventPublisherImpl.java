package graduationProject.IoTSystem.deviceConnector.rabbitMQ;

import graduationProject.IoTSystem.deviceConnector.dto.DeviceStateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

import static graduationProject.IoTSystem.deviceConnector.rabbitMQ.RabbitmqConfig.*;


@Service
@RequiredArgsConstructor
public class MQEventPublisherImpl implements MQEventPublisher{
    private final RabbitTemplate rabbitTemplate;
    @Override
    public void publishDeviceStateEvent(DeviceStateDto deviceStateDto) {
        deviceStateDto.setNotificationType("device-state");
        deviceStateDto.setFrom("device-connector");
        rabbitTemplate.convertAndSend(SYS_NTF_EXCHANGE, "",deviceStateDto);
    }

    @Override
    public void publishDeviceInfoEvent(Map<String,Object> deviceInfoDto) {
        rabbitTemplate.convertAndSend(IoT_DBC_EXCHANGE, DEVICE_INFO_QUEUE,deviceInfoDto);
    }
}
