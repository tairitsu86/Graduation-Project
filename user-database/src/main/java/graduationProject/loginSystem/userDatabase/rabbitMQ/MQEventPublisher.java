package graduationProject.loginSystem.userDatabase.rabbitMQ;


import graduationProject.loginSystem.userDatabase.dto.LoginEventDto;
import graduationProject.loginSystem.userDatabase.dto.LogoutEventDto;

public interface MQEventPublisher {
    void publishLoginEvent(LoginEventDto loginEventDto);
    void publishLogoutEvent(LogoutEventDto logoutEventDto);
}
