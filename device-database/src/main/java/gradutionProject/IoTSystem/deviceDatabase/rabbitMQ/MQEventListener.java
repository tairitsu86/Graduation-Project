package gradutionProject.IoTSystem.deviceDatabase.rabbitMQ;

import gradutionProject.IoTSystem.deviceDatabase.dto.DeviceControlDto;
import gradutionProject.IoTSystem.deviceDatabase.dto.UserControlDto;
import gradutionProject.IoTSystem.deviceDatabase.entity.Device;
import gradutionProject.IoTSystem.deviceDatabase.repository.DeviceRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static gradutionProject.IoTSystem.deviceDatabase.rabbitMQ.RabbitmqConfig.DEVICE_INFO_QUEUE;
import static gradutionProject.IoTSystem.deviceDatabase.rabbitMQ.RabbitmqConfig.USER_CONTROL_QUEUE;

@Slf4j
@RequiredArgsConstructor
@Component
public class MQEventListener {
    private final DeviceRepositoryService deviceRepositoryService;
    private final MQEventPublisher mqEventPublisher;
    @RabbitListener(queues={DEVICE_INFO_QUEUE})
    public void receiveDeviceInfoEvent(Device device) {
        log.info("Device info event: {}", device);
        deviceRepositoryService.updateDeviceInfo(device);
    }

    @RabbitListener(queues={USER_CONTROL_QUEUE})
    public void receiveUserControlEvent(UserControlDto userControlDto) {
        log.info("User control event: {}", userControlDto);
        if(!deviceRepositoryService.checkPermission(userControlDto.getUsername(), userControlDto.getDeviceId())){
            log.info("No permission!");
            return;
        }

        mqEventPublisher.publishDeviceControl(
                DeviceControlDto.builder()
                        .executor(userControlDto.getUsername())
                        .functionType(deviceRepositoryService.getFunctionType(userControlDto.getDeviceId(), userControlDto.getFunctionId()))
                        .deviceId(userControlDto.getDeviceId())
                        .functionId(userControlDto.getFunctionId())
                        .functionPara(userControlDto.getFunctionPara())
                        .build()
        );
    }
}
