package graduationProject.IoTSystem.deviceDatabase.rabbitMQ;

import graduationProject.IoTSystem.deviceDatabase.dto.DeviceControlDto;
import graduationProject.IoTSystem.deviceDatabase.dto.DeviceDto;
import graduationProject.IoTSystem.deviceDatabase.dto.UserControlDto;
import graduationProject.IoTSystem.deviceDatabase.repository.RepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static graduationProject.IoTSystem.deviceDatabase.rabbitMQ.RabbitmqConfig.DEVICE_INFO_QUEUE;
import static graduationProject.IoTSystem.deviceDatabase.rabbitMQ.RabbitmqConfig.USER_CONTROL_QUEUE;

@Slf4j
@RequiredArgsConstructor
@Component
public class MQEventListener {
    private final RepositoryService repositoryService;
    private final MQEventPublisher mqEventPublisher;
    @RabbitListener(queues={DEVICE_INFO_QUEUE})
    public void receiveDeviceInfoEvent(DeviceDto deviceDto) {
        log.info("Device info event: {}", deviceDto);
        repositoryService.updateDeviceInfo(deviceDto);
    }

    @RabbitListener(queues={USER_CONTROL_QUEUE})
    public void receiveUserControlEvent(UserControlDto userControlDto) {
        log.info("User control event: {}", userControlDto);
        try {
            if(!repositoryService.checkPermission(userControlDto.getUsername(), userControlDto.getDeviceId())){
                log.info("No permission!");
                return;
            }

            if(userControlDto.getFunctionId()<0 && repositoryService.isOwner(userControlDto.getDeviceId(), userControlDto.getUsername())){
                if(userControlDto.getFunctionId()==-1){
                    repositoryService.grantPermissionToUser(userControlDto.getDeviceId(), userControlDto.getParameters().get("USERNAME"));
                    return;
                }
                if (userControlDto.getFunctionId()==-2) {
                    repositoryService.grantPermissionToGroup(userControlDto.getDeviceId(), userControlDto.getParameters().get("GROUP_ID"));
                    return;
                }
                if (userControlDto.getFunctionId()==-3) {
                    repositoryService.removePermissionFromUser(userControlDto.getDeviceId(), userControlDto.getParameters().get("USERNAME"));
                    return;
                }
                if (userControlDto.getFunctionId()==-4) {
                    repositoryService.removePermissionFromGroup(userControlDto.getDeviceId(), userControlDto.getParameters().get("GROUP_ID"));
                    return;
                }
            }
            mqEventPublisher.publishDeviceControl(
                    DeviceControlDto.builder()
                            .executor(userControlDto.getUsername())
                            .deviceId(userControlDto.getDeviceId())
                            .functionId(userControlDto.getFunctionId())
                            .parameters(userControlDto.getParameters())
                            .build()
            );
        }catch (Exception e){
            log.info("Something go wrong: {}",e.getMessage(),e);
        }


    }
}
