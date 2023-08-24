package gradutionProject.IMUISystem.loginTracker.rabbitMQ;

import gradutionProject.IMUISystem.loginTracker.dto.LoginEventDto;
import gradutionProject.IMUISystem.loginTracker.entity.IMUserData;
import gradutionProject.IMUISystem.loginTracker.repository.LoginUserRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static gradutionProject.IMUISystem.loginTracker.rabbitMQ.RabbitmqConfig.loginEventQueue;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoginEventListener {
    private final LoginUserRepositoryService loginUserRepositoryService;
    @RabbitListener(queues={loginEventQueue})
    public void receive(LoginEventDto loginEventDto) {
        log.info(loginEventDto.toString());
        if(!loginEventDto.getPlatformType().equals("Instant-Messaging")) return;
        loginUserRepositoryService.login(
                IMUserData.builder()
                        .platform(loginEventDto.getFromPlatform())
                        .userId(loginEventDto.getPlatformUserId())
                        .build(),
                loginEventDto.getUsername()
        );
    }

}
