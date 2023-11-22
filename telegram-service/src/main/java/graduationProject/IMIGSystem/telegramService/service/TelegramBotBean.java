package graduationProject.IMIGSystem.telegramService.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pengrad.telegrambot.TelegramBot;

@Configuration
public class TelegramBotBean {
    @Value("${my_env.telegramBotToken}")
    private String TELEGRAM_BOT_TOKEN;

    @Bean
    public TelegramBot getTelegramBot() {
        return new TelegramBot(System.getenv("TELEGRAM_BOT_TOKEN"));
    }
}
