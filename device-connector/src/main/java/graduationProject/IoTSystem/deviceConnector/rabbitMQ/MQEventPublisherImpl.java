package graduationProject.IoTSystem.deviceConnector.rabbitMQ;

import graduationProject.IoTSystem.deviceConnector.dto.DeviceInfoDto;
import graduationProject.IoTSystem.deviceConnector.dto.DeviceStateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static graduationProject.IoTSystem.deviceConnector.rabbitMQ.RabbitmqConfig.*;


@Service
@RequiredArgsConstructor
public class MQEventPublisherImpl implements MQEventPublisher{
    private final RabbitTemplate rabbitTemplate;
    @Override
    public void publishDeviceStateEvent(DeviceStateDto deviceStateDto) {
        rabbitTemplate.convertAndSend(MQ_EXCHANGE, DEVICE_STATE_QUEUE,deviceStateDto);
    }

    @Override
    public void publishDeviceInfoEvent(DeviceInfoDto deviceInfoDto) {
        rabbitTemplate.convertAndSend(MQ_EXCHANGE, DEVICE_INFO_QUEUE,deviceInfoDto);
    }
}
