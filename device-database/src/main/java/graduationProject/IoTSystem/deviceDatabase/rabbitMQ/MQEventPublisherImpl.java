package graduationProject.IoTSystem.deviceDatabase.rabbitMQ;

import graduationProject.IoTSystem.deviceDatabase.dto.DeviceControlDto;
import graduationProject.IoTSystem.deviceDatabase.dto.DeviceStateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static graduationProject.IoTSystem.deviceDatabase.rabbitMQ.RabbitmqConfig.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MQEventPublisherImpl implements MQEventPublisher{
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishDeviceControl(DeviceControlDto deviceControlDto) {
        rabbitTemplate.convertAndSend(IoT_DBC_EXCHANGE, DEVICE_CONTROL_QUEUE, deviceControlDto);
    }
    @Override
    public void publishDeviceStateEvent(DeviceStateDto deviceStateDto) {
        deviceStateDto.setNotificationType("device-state");
        deviceStateDto.setFrom("device-database");
        rabbitTemplate.convertAndSend(SYS_NTF_EXCHANGE, "#", deviceStateDto);
        log.info("Send to exchange [{}] with routingKey [{}], event [{}]", SYS_NTF_EXCHANGE, "#", deviceStateDto);
    }
}
