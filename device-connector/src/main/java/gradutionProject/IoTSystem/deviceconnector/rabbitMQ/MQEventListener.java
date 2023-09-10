package gradutionProject.IoTSystem.deviceconnector.rabbitMQ;

import gradutionProject.IoTSystem.deviceconnector.dto.DeviceControlDto;
import gradutionProject.IoTSystem.deviceconnector.mqtt.MQTTService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static gradutionProject.IoTSystem.deviceconnector.rabbitMQ.RabbitmqConfig.DEVICE_CONTROL_QUEUE;

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
