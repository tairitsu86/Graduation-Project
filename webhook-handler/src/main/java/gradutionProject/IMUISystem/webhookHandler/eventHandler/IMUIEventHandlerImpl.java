package gradutionProject.IMUISystem.webhookHandler.eventHandler;


import gradutionProject.IMUISystem.webhookHandler.dto.MessageEvent;
import gradutionProject.IMUISystem.webhookHandler.rabbitMQ.MQEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IMUIEventHandlerImpl implements IMUIEventHandler{
    private final MQEventPublisher mqEventPublisher;

    @Override
    public void messageEventHandler(String message) {
        mqEventPublisher.publishEvent(MessageEvent.builder()
                .message(message).build()
        );
    }
}
