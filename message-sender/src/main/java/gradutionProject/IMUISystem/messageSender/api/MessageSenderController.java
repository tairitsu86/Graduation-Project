package gradutionProject.IMUISystem.messageSender.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageSenderController {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String home(){
        return "Hello! This is message sender!";
    }
}
