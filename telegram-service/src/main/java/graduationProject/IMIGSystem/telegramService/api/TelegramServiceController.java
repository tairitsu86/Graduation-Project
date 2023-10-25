package graduationProject.IMIGSystem.telegramService.api;

import com.pengrad.telegrambot.BotUtils;
import graduationProject.IMIGSystem.telegramService.service.TelegramBotHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TelegramServiceController {
    private final TelegramBotHandler telegramBotHandler;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String home(){
        return "Hello! This is telegram service!";
    }


    @PostMapping("${my_env.telegramWebHook}")
    public void telegramWebHook(@RequestBody String update) {
        System.out.println(update);
        telegramBotHandler.handleUpdate(BotUtils.parseUpdate(update));
    }
}
