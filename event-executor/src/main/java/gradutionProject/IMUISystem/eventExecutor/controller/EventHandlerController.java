package gradutionProject.IMUISystem.eventExecutor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class EventHandlerController {

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String home(){
        return "Hello! This is event executor!";
    }

    @PostMapping("/comm/config/new")
    @ResponseStatus(HttpStatus.CREATED)
    public void newCommConfig(){
        //TODO
    }


}
