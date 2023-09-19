package gradutionProject.IoTSystem.deviceDatabase.rabbitMQ;

import gradutionProject.IoTSystem.deviceDatabase.dto.DeviceControlDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static gradutionProject.IoTSystem.deviceDatabase.rabbitMQ.RabbitmqConfig.DEVICE_CONTROL_QUEUE;
import static gradutionProject.IoTSystem.deviceDatabase.rabbitMQ.RabbitmqConfig.MQ_EXCHANGE;

@Service
@RequiredArgsConstructor
public class MQEventPublisherImpl implements MQEventPublisher{
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishDeviceControl(DeviceControlDto deviceControlDto) {
        rabbitTemplate.convertAndSend(MQ_EXCHANGE, DEVICE_CONTROL_QUEUE, deviceControlDto);
    }
}
