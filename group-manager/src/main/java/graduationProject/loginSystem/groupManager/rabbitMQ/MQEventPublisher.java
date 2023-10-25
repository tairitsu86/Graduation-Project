package graduationProject.loginSystem.groupManager.rabbitMQ;

import graduationProject.loginSystem.groupManager.dto.NotificationDto;

public interface MQEventPublisher {
    void publishNotificationEvent(NotificationDto notificationDto);
}
