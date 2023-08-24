package gradutionProject.loginSystem.userDatabase.rabbitMQ;


import gradutionProject.loginSystem.userDatabase.dto.LoginEventDto;

public interface MQEventPublisher {
    void publishLoginEvent(LoginEventDto loginEventDto);
}
