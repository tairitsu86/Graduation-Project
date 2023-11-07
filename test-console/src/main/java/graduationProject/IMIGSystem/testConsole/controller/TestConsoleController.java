package graduationProject.IMIGSystem.testConsole.controller;

import graduationProject.IMIGSystem.testConsole.dto.IMUserData;
import graduationProject.IMIGSystem.testConsole.dto.MessageEventDto;
import graduationProject.IMIGSystem.testConsole.dto.WebhookDto;
import graduationProject.IMIGSystem.testConsole.rabbitMQ.MQEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
@Slf4j
public class TestConsoleController {
    private final MQEventPublisher mqEventPublisher;
    public static String consoleText = null;
    public static void show(String text){
        String time = ZonedDateTime.now(ZoneId.of("UTC+8")).format(DateTimeFormatter.ofPattern("[yyyy-MM-dd HH:mm:ss]"));
        text = String.format("[%s] %s", time, text.replace("\n",String.format("\n[%s] ",time)));
        if(consoleText==null)
            consoleText = text;
        else
            consoleText = String.format("%s\n%s",consoleText, text);
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String home(Model model){
        return "home";
    }
    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String processForm(@RequestParam String message, @RequestParam String userId, Model model) {
        show(String.format("User[%s] send: %s",userId, message));
        model.addAttribute("consoleText", consoleText);
        newMessage(
                WebhookDto.builder()
                        .userId(userId)
                        .message(message)
                        .build()
        );
        return "home";
    }

    @PostMapping("/webhook")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void newMessage(@RequestBody WebhookDto webhookDto){
        mqEventPublisher.publishEvent(
                MessageEventDto.builder()
                        .imUserData(
                                IMUserData.builder()
                                        .platform("TEST")
                                        .userId(webhookDto.getUserId())
                                        .build()
                        )
                        .message(webhookDto.getMessage())
                        .build()
        );
    }
    @GetMapping("/fetch-console-text")
    @ResponseBody
    public String fetchConsoleText() {
        return consoleText;
    }

}
