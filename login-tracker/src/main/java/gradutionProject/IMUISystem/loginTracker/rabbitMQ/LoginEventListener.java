package gradutionProject.IMUISystem.loginTracker.rabbitMQ;

import gradutionProject.IMUISystem.loginTracker.dto.LoginEventDto;
import gradutionProject.IMUISystem.loginTracker.dto.LogoutEventDto;
import gradutionProject.IMUISystem.loginTracker.entity.IMUserData;
import gradutionProject.IMUISystem.loginTracker.repository.LoginUserRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static gradutionProject.IMUISystem.loginTracker.rabbitMQ.RabbitmqConfig.loginEventQueue;
import static gradutionProject.IMUISystem.loginTracker.rabbitMQ.RabbitmqConfig.logoutEventQueue;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoginEventListener {
    private final LoginUserRepositoryService loginUserRepositoryService;
    @RabbitListener(queues={loginEventQueue})
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
    @RabbitListener(queues={logoutEventQueue})
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
