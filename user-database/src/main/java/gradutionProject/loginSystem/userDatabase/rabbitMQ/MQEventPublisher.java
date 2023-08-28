package gradutionProject.loginSystem.userDatabase.rabbitMQ;


import gradutionProject.loginSystem.userDatabase.dto.LoginEventDto;
import gradutionProject.loginSystem.userDatabase.dto.LogoutEventDto;

public interface MQEventPublisher {
    void publishLoginEvent(LoginEventDto loginEventDto);
    void publishLogoutEvent(LogoutEventDto logoutEventDto);
}
