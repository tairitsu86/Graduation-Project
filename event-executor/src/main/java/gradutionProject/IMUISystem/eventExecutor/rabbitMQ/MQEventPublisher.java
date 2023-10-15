package gradutionProject.IMUISystem.eventExecutor.rabbitMQ;

import gradutionProject.IMUISystem.eventExecutor.dto.CommConfigDto;
import gradutionProject.IMUISystem.eventExecutor.dto.NewCustomizeEventDto;
import gradutionProject.IMUISystem.eventExecutor.dto.SendingEventDto;
import gradutionProject.IMUISystem.eventExecutor.entity.MenuOption;

import java.util.List;
import java.util.Map;

public interface MQEventPublisher {
    void publishSendingEvent(SendingEventDto sendingEventDto);

    void notifyUser(List<String> users, String message);

    void publishCustomEvent(CommConfigDto commConfigDto);

    void publishMenuEvent(NewCustomizeEventDto newCustomizeEventDto);

    void newMenu(String description, List<MenuOption> options, Map<String,String> parameters);
}
