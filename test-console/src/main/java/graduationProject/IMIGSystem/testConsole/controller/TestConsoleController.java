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

@Controller
@RequiredArgsConstructor
@Slf4j
public class TestConsoleController {
    private final MQEventPublisher mqEventPublisher;
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String home(Model model){
        return "home";
    }
    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String processForm(@RequestParam String message, @RequestParam String userId, Model model) {
        String newMessage = String.format("User [%s]: %s",userId, message);
        newMessage(
                WebhookDto.builder()
                        .userId(userId)
                        .message(message)
                        .build()
        );
        model.addAttribute("consoleText", newMessage);
        return "home";
    }

    @PostMapping("/webhook")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void newMessage(@RequestBody WebhookDto webhookDto){
        log.info("user [{}] send: {}", webhookDto.getUserId(), webhookDto.getMessage());
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
}
