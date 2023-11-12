package graduationProject.IoTSystem.deviceDatabase.rabbitMQ;

import graduationProject.IoTSystem.deviceDatabase.api.exception.NoPermissionException;
import graduationProject.IoTSystem.deviceDatabase.dto.DeviceControlDto;
import graduationProject.IoTSystem.deviceDatabase.dto.DeviceStateDto;
import graduationProject.IoTSystem.deviceDatabase.dto.UserControlDto;
import graduationProject.IoTSystem.deviceDatabase.entity.Device;
import graduationProject.IoTSystem.deviceDatabase.repository.RepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static graduationProject.IoTSystem.deviceDatabase.rabbitMQ.RabbitmqConfig.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class MQEventListener {
    private final RepositoryService repositoryService;
    private final MQEventPublisher mqEventPublisher;
    @RabbitListener(queues={DEVICE_INFO_QUEUE})
    public void receive(Device device) {
        log.info("Device info event: {}", device);
        repositoryService.updateDeviceInfo(device);
    }

    @RabbitListener(queues={USER_CONTROL_QUEUE})
    public void receive(UserControlDto userControlDto) {
        log.info("User control event: {}", userControlDto);
        try {
            if(!repositoryService.checkPermission(userControlDto.getUsername(), userControlDto.getDeviceId()))
                throw new NoPermissionException(userControlDto.getUsername(), userControlDto.getDeviceId());

            mqEventPublisher.publishDeviceControl(
                    DeviceControlDto.builder()
                            .executor(userControlDto.getUsername())
                            .deviceId(userControlDto.getDeviceId())
                            .functionId(userControlDto.getFunctionId())
                            .parameter(userControlDto.getParameter())
                            .build()
            );
        }catch (Exception e){
            log.info("Something go wrong: {}",e.getMessage(),e);
        }
    }
    @RabbitListener(queues={DEVICE_STATE_QUEUE})
    public void receive(DeviceStateDto deviceStateDto) {
        log.info("Device state event: {}", deviceStateDto);
        deviceStateDto.setDeviceName(repositoryService.getDevice(deviceStateDto.getDeviceId()).getDeviceName());
        mqEventPublisher.publishDeviceStateEvent(deviceStateDto);
    }
}
