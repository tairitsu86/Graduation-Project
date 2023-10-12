package gradutionProject.IMUISystem.eventExecutor.controller;

import gradutionProject.IMUISystem.eventExecutor.entity.CommConfig;
import gradutionProject.IMUISystem.eventExecutor.entity.RespondConfig;
import gradutionProject.IMUISystem.eventExecutor.repository.RepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class EventHandlerController {
    private final RepositoryService repositoryService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String home(){
        return "Hello! This is event executor!";
    }

    @GetMapping("/events/{eventName}/comm")
    @ResponseStatus(HttpStatus.OK)
    public CommConfig getCommConfig(@PathVariable String eventName){
        return repositoryService.getCommConfig(eventName);
    }

    @PostMapping("/events/{eventName}/comm/new")
    @ResponseStatus(HttpStatus.CREATED)
    public void newCommConfig(@PathVariable String eventName, @RequestBody CommConfig commConfig){
        commConfig.setEventName(eventName);
        repositoryService.newCommConfig(commConfig);
    }

    @DeleteMapping("/events/{eventName}/comm/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommConfig(@PathVariable String eventName){
        repositoryService.deleteCommConfig(eventName);
    }

    @GetMapping("/events/{eventName}/notify")
    @ResponseStatus(HttpStatus.OK)
    public RespondConfig getRespondConfig(@PathVariable String eventName){
        return repositoryService.getRespondConfig(eventName);
    }

    @PostMapping("/events/{eventName}/notify/new")
    @ResponseStatus(HttpStatus.CREATED)
    public void newRespondConfig(@PathVariable String eventName,@RequestBody RespondConfig respondConfig){
        respondConfig.setEventName(eventName);
        repositoryService.newRespondConfig(respondConfig);
    }

    @DeleteMapping("/events/{eventName}/notify/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRespondConfig(@PathVariable String eventName){
        repositoryService.deleteRespondConfig(eventName);
    }


}
