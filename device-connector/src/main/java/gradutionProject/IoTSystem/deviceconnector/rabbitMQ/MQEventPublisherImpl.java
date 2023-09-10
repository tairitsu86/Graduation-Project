package gradutionProject.IoTSystem.deviceconnector.rabbitMQ;

import gradutionProject.IoTSystem.deviceconnector.dto.DeviceStateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static gradutionProject.IoTSystem.deviceconnector.rabbitMQ.RabbitmqConfig.DEVICE_STATE_QUEUE;
import static gradutionProject.IoTSystem.deviceconnector.rabbitMQ.RabbitmqConfig.MQ_EXCHANGE;


@Service
@RequiredArgsConstructor
public class MQEventPublisherImpl implements MQEventPublisher{
    private final RabbitTemplate rabbitTemplate;
    @Override
    public void publishDeviceStateEvent(DeviceStateDto deviceStateDto) {
        rabbitTemplate.convertAndSend(MQ_EXCHANGE, DEVICE_STATE_QUEUE,deviceStateDto);
    }
}
