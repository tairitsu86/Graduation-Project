package graduationProject.IMUISystem.loginTracker.rabbitMQ;

import graduationProject.IMUISystem.loginTracker.repository.LoginUserRepositoryService;
import graduationProject.IMUISystem.loginTracker.dto.LoginEventDto;
import graduationProject.IMUISystem.loginTracker.dto.LogoutEventDto;
import graduationProject.IMUISystem.loginTracker.entity.IMUserData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static graduationProject.IMUISystem.loginTracker.rabbitMQ.RabbitmqConfig.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoginEventListener {
    private final LoginUserRepositoryService loginUserRepositoryService;
    @RabbitListener(queues={LOGIN_LOG_QUEUE})
    public void receiveLoginEvent(LoginEventDto loginEventDto) {
        if(loginEventDto==null||loginEventDto.getPlatformType()==null||loginEventDto.getFromPlatform()==null||loginEventDto.getPlatformUserId()==null||loginEventDto.getUsername()==null) {
            log.info("Null Login event:"+(loginEventDto==null?null:loginEventDto.toString()));
            return;
        }
        log.info("Login event:"+loginEventDto.toString());
        if(!loginEventDto.getPlatformType().equals("Instant-Messaging")) return;
        loginUserRepositoryService.login(
                IMUserData.builder()
                        .platform(loginEventDto.getFromPlatform())
                        .userId(loginEventDto.getPlatformUserId())
                        .build(),
                loginEventDto.getUsername()
        );
    }
    @RabbitListener(queues={LOGOUT_LOG_QUEUE})
    public void receiveLogoutEvent(LogoutEventDto logoutEventDto) {
        if(logoutEventDto==null||logoutEventDto.getPlatformType()==null||logoutEventDto.getFromPlatform()==null||logoutEventDto.getPlatformUserId()==null) {
            log.info("Null Logout event:"+(logoutEventDto==null?null:logoutEventDto.toString()));
            return;
        }
        log.info("Logout event:"+logoutEventDto.toString());
        if(!logoutEventDto.getPlatformType().equals("Instant-Messaging")) return;
        loginUserRepositoryService.logout(
                IMUserData.builder()
                        .platform(logoutEventDto.getFromPlatform())
                        .userId(logoutEventDto.getPlatformUserId())
                        .build()
        );
    }

}
