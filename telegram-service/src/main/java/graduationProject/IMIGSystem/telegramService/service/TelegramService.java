package graduationProject.IMIGSystem.telegramService.service;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class TelegramService implements IMService{

    private final TelegramBot telegramBot;

    @Override
    public void SendTextMessage(String userId, String message) {
        long chatId = Long.parseLong(userId);
        sendMessage(
                new SendMessage(chatId, message)
                        .parseMode(ParseMode.HTML)
                        .disableWebPagePreview(false)
                        .disableNotification(false)
        );
    }
    public void sendMessage(SendMessage request){
        telegramBot.execute(request, new Callback<SendMessage, SendResponse>() {
            @Override
            public void onResponse(SendMessage request, SendResponse response) {
                System.out.println(response);
            }
            @Override
            public void onFailure(SendMessage request, IOException e) {
                e.printStackTrace();
            }
        });
    }
}
