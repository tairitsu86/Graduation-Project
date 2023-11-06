package graduationProject.IMIGSystem.testConsole.rabbitMQ;


import graduationProject.IMIGSystem.testConsole.dto.MessageEventDto;

public interface MQEventPublisher {
    void publishEvent(MessageEventDto event);

}
