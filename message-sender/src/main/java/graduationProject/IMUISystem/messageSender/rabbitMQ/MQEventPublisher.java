package graduationProject.IMUISystem.messageSender.rabbitMQ;

import graduationProject.IMUISystem.messageSender.dto.SendingServiceDto;

public interface MQEventPublisher {
    void publishSendingServiceEvent(String platform, SendingServiceDto sendingServiceDto);

}
