package gradutionProject.loginSystem.groupManager.rabbitMQ;

import gradutionProject.loginSystem.groupManager.dto.NotificationDto;

public interface MQEventPublisher {
    void publishNotificationEvent(NotificationDto notificationDto);
}
