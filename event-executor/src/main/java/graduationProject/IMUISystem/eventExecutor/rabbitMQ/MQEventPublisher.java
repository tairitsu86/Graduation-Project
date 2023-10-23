package graduationProject.IMUISystem.eventExecutor.rabbitMQ;

import graduationProject.IMUISystem.eventExecutor.dto.CommConfigDto;
import graduationProject.IMUISystem.eventExecutor.dto.NewCustomizeEventDto;
import graduationProject.IMUISystem.eventExecutor.dto.SendingEventDto;
import graduationProject.IMUISystem.eventExecutor.entity.MenuOption;

import java.util.List;
import java.util.Map;

public interface MQEventPublisher {
    void publishSendingEvent(SendingEventDto sendingEventDto);

    void notifyUser(List<String> users, String message);

    void publishCustomEvent(CommConfigDto commConfigDto);

    void publishMenuEvent(NewCustomizeEventDto newCustomizeEventDto);

    void newMenu(String username, String description, List<MenuOption> options, Map<String,Object> parameters);
}
