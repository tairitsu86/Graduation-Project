package graduationProject.IoTSystem.deviceConnector.rabbitMQ;

import graduationProject.IoTSystem.deviceConnector.dto.DeviceStateDto;
import graduationProject.IoTSystem.deviceConnector.mqtt.MQTTService;
import graduationProject.IoTSystem.deviceConnector.dto.DeviceControlDto;
import graduationProject.IoTSystem.deviceConnector.repository.DeviceStateRepository;
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
    private final MQEventPublisher mqEventPublisher;
    private final DeviceStateRepository deviceStateRepository;
    @RabbitListener(queues={DEVICE_CONTROL_QUEUE})
    public void receive(DeviceControlDto deviceControlDto) {
        log.info("Device control event: {}",deviceControlDto);
        try {
            switch (deviceControlDto.getFunctionType()){
                case CONTROL -> mqttService.controlDevice(deviceControlDto);
                case STATE ->
                    mqEventPublisher.publishDeviceStateEvent(
                            DeviceStateDto.builder()
                                    .deviceId(deviceControlDto.getDeviceId())
                                    .executor(deviceControlDto.getExecutor())
                                    .functionType(deviceControlDto.getFunctionType())
                                    .stateName(deviceControlDto.getFunctionPara())
                                    .stateValue(deviceStateRepository.getState(deviceControlDto.getDeviceId(), deviceControlDto.getFunctionPara()).get(0).getStateValue())
                                    .build());
            }

        }catch (NullPointerException e){
            log.info("Some field is null!");
        }
    }
}
