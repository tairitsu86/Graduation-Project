package gradutionProject.IMUISystem.eventExecutor.controller;

import gradutionProject.IMUISystem.eventExecutor.entity.CommConfig;
import gradutionProject.IMUISystem.eventExecutor.entity.NotifyConfig;
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
    public NotifyConfig getNotifyConfig(@PathVariable String eventName){
        return repositoryService.getNotifyConfig(eventName);
    }

    @PostMapping("/events/{eventName}/notify/new")
    @ResponseStatus(HttpStatus.CREATED)
    public void newNotifyConfig(@PathVariable String eventName,@RequestBody NotifyConfig notifyConfig){
        notifyConfig.setEventName(eventName);
        repositoryService.newNotifyConfig(notifyConfig);
    }

    @DeleteMapping("/events/{eventName}/notify/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNotifyConfig(@PathVariable String eventName){
        repositoryService.deleteNotifyConfig(eventName);
    }


}
