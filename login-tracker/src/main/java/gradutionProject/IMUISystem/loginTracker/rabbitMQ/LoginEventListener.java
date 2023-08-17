package gradutionProject.IMUISystem.loginTracker.rabbitMQ;

import gradutionProject.IMUISystem.loginTracker.dto.LoginEventDto;
import gradutionProject.IMUISystem.loginTracker.repository.LoginUserRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoginEventListener {
    private final LoginUserRepositoryService loginUserRepositoryService;
    @RabbitListener(queues={"IM-UI/Login-event"})
    public void receive(LoginEventDto loginEventDto) {
        log.info(loginEventDto.toString());
        if(loginEventDto.getState().equalsIgnoreCase("LOGIN")){
            loginUserRepositoryService.login(loginEventDto.getImUserData(),loginEventDto.getUsername());
        }else if(loginEventDto.getState().equalsIgnoreCase("LOGOUT")){
            loginUserRepositoryService.logout(loginEventDto.getImUserData());
        }
    }
}
