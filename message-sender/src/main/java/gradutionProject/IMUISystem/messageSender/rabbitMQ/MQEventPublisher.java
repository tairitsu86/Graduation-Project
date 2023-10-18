package gradutionProject.IMUISystem.messageSender.rabbitMQ;

import gradutionProject.IMUISystem.messageSender.dto.SendingServiceDto;

import java.util.List;

public interface MQEventPublisher {
    void publishSendingServiceEvent(String platform, SendingServiceDto sendingServiceDto);

}
