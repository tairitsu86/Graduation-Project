package graduationProject.IMIGSystem.telegramService.service;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import graduationProject.IMIGSystem.telegramService.dto.IMUserData;
import graduationProject.IMIGSystem.telegramService.dto.MessageEventDto;
import graduationProject.IMIGSystem.telegramService.rabbitMQ.MQEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TelegramBotHandler {
    private final MQEventPublisher mqEventPublisher;

    public void handleUpdate(Update update) {
        System.out.println(update);
        if(update.message()!=null)
            messageUpdateHandler(update.message());
    }
    public void messageUpdateHandler(Message m){
        String message = m.text();
        String userId = m.chat().id().toString();
        log.info("User id: {}, Message: {}" , userId, message);
        mqEventPublisher.publishEvent(
                MessageEventDto.builder()
                        .imUserData(
                                IMUserData.builder()
                                        .platform("TELEGRAM")
                                        .userId(userId)
                                        .build()
                        )
                        .message(message)
                        .build()
        );
    }
}
