package graduationProject.IoTSystem.deviceConnector.rabbitMQ;

import graduationProject.IoTSystem.deviceConnector.mqtt.MQTTService;
import graduationProject.IoTSystem.deviceConnector.dto.DeviceControlDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static graduationProject.IoTSystem.deviceConnector.rabbitMQ.RabbitmqConfig.DEVICE_CONTROL_QUEUE;

@Slf4j
@RequiredArgsConstructor
@Component
public class MQEventListener {
    private final MQTTService mqttService;
    @RabbitListener(queues={DEVICE_CONTROL_QUEUE})
    public void receive(DeviceControlDto deviceControlDto) {
        if(deviceControlDto==null||deviceControlDto.getDeviceId()==null)return;
        log.info("Device control event: "+deviceControlDto.toString());
        mqttService.controlDevice(deviceControlDto.getDeviceId(), deviceControlDto.getFunctionId(),deviceControlDto.getFunctionPara());
    }
}
