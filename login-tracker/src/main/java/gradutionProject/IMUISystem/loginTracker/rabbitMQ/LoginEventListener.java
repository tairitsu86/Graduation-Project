package gradutionProject.IMUISystem.loginTracker.rabbitMQ;

import gradutionProject.IMUISystem.loginTracker.dto.LoginEventDto;
import gradutionProject.IMUISystem.loginTracker.repository.LoginUserRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Slf4j
@RequiredArgsConstructor
public class LoginEventListener {
    private final LoginUserRepositoryService loginUserRepositoryService;
    @RabbitListener(queues={"IM-UI/Login-event"})
    public void receive(LoginEventDto loginEventDto) {
        log.info("Login-event:",loginEventDto);
        if(loginEventDto.isLogin()){
            loginUserRepositoryService.login(loginEventDto.getImUserData(),loginEventDto.getUsername());
        }else{
            loginUserRepositoryService.logout(loginEventDto.getImUserData());
        }
    }
}
