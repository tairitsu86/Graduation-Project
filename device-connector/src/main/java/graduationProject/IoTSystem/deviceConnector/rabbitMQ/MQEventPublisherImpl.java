package graduationProject.IoTSystem.deviceConnector.rabbitMQ;

import graduationProject.IoTSystem.deviceConnector.dto.DeviceBasicDataDto;
import graduationProject.IoTSystem.deviceConnector.dto.DeviceStateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static graduationProject.IoTSystem.deviceConnector.rabbitMQ.RabbitmqConfig.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class MQEventPublisherImpl implements MQEventPublisher{
    private final RabbitTemplate rabbitTemplate;
    @Override
    public void publishDeviceStateEvent(DeviceStateDto deviceStateDto) {
        rabbitTemplate.convertAndSend(IoT_DBC_EXCHANGE, DEVICE_STATE_QUEUE, deviceStateDto);
        log.info("Send to exchange [{}] with routingKey [{}], event [{}]", IoT_DBC_EXCHANGE, DEVICE_STATE_QUEUE, deviceStateDto);
    }

    @Override
    public void publishDeviceInfoEvent(DeviceBasicDataDto deviceBasicDataDto) {
        rabbitTemplate.convertAndSend(IoT_DBC_EXCHANGE, DEVICE_INFO_QUEUE, deviceBasicDataDto);
        log.info("Send to exchange [{}] with routingKey [{}], event [{}]", IoT_DBC_EXCHANGE, DEVICE_INFO_QUEUE, deviceBasicDataDto);
    }
}
