package gradutionProject.IMIGSystem.lineService.service;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import gradutionProject.IMIGSystem.lineService.dto.IMUserData;
import gradutionProject.IMIGSystem.lineService.dto.MessageEventDto;
import gradutionProject.IMIGSystem.lineService.rabbitMQ.MQEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@LineMessageHandler
public class LineBotHandler {
    private final MQEventPublisher mqEventPublisher;
    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        log.info("event: " + event);
        final String text = event.getMessage().getText();
        final String userId = event.getSource().getUserId();
        mqEventPublisher.publishEvent(
                MessageEventDto.builder()
                        .imUserData(
                                IMUserData.builder()
                                        .platform("LINE")
                                        .userId(userId)
                                        .build()
                        )
                        .message(text)
                        .build()
        );
    }
}
