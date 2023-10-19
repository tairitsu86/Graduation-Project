package graduationProject.IoTSystem.deviceDatabase.rabbitMQ;

import graduationProject.IoTSystem.deviceDatabase.dto.DeviceControlDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static graduationProject.IoTSystem.deviceDatabase.rabbitMQ.RabbitmqConfig.*;

@Service
@RequiredArgsConstructor
public class MQEventPublisherImpl implements MQEventPublisher{
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishDeviceControl(DeviceControlDto deviceControlDto) {
        rabbitTemplate.convertAndSend(IoT_DBC_EXCHANGE, DEVICE_CONTROL_QUEUE, deviceControlDto);
    }
}
