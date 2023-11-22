package gradutionProject.IMUISystem.webhookHandler.instantMessagingPlatform.line;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import gradutionProject.IMUISystem.webhookHandler.eventHandler.IMUIEventHandler;
import gradutionProject.IMUISystem.webhookHandler.instantMessagingPlatform.InstantMessagingPlatform;
import lombok.RequiredArgsConstructor;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@LineMessageHandler
public class LineBotHandler {
    private final IMUIEventHandler imuiEventHandler;
    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        log.info("event: " + event);
        final String text = event.getMessage().getText();
        final String userId = event.getSource().getUserId();
        imuiEventHandler.messageEventHandler(InstantMessagingPlatform.LINE,userId,text);
    }
}
