package gradutionProject.IMUISystem.eventExecutor.controller;

import gradutionProject.IMUISystem.eventExecutor.entity.CommConfig;
import gradutionProject.IMUISystem.eventExecutor.entity.NotifyConfig;
import gradutionProject.IMUISystem.eventExecutor.repository.RepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class EventHandlerController {
    private final RepositoryService repositoryService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String home(){
        return "Hello! This is event executor!";
    }

    @PostMapping("/events/{eventName}/comm/new")
    @ResponseStatus(HttpStatus.CREATED)
    public void newCommConfig(@PathVariable String eventName, @RequestBody CommConfig commConfig){
        commConfig.setEventName(eventName);
        repositoryService.newCommConfig(commConfig);
    }

    @PatchMapping("/events/{eventName}/comm/alter")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterCommConfig(@PathVariable String eventName,@RequestBody Map<String,String> alterField){
        //TODO
    }

    @DeleteMapping("/events/{eventName}/comm/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommConfig(@PathVariable String eventName){
        repositoryService.deleteCommConfig(eventName);
    }

    @PostMapping("/events/{eventName}/notify/new")
    @ResponseStatus(HttpStatus.CREATED)
    public void newNotifyConfig(@PathVariable String eventName,@RequestBody NotifyConfig notifyConfig){
        notifyConfig.setEventName(eventName);
        repositoryService.newNotifyConfig(notifyConfig);
    }

    @PatchMapping("/events/{eventName}/notify/alter")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterNotifyConfig(@PathVariable String eventName,@RequestBody Map<String,String> alterField){
        //TODO
    }

    @DeleteMapping("/events/{eventName}/notify/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNotifyConfig(@PathVariable String eventName){
        repositoryService.deleteNotifyConfig(eventName);
    }


}
