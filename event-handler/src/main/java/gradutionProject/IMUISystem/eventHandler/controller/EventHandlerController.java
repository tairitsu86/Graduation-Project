package gradutionProject.IMUISystem.eventHandler.controller;

import gradutionProject.IMUISystem.eventHandler.dto.CustomizeEventDto;
import gradutionProject.IMUISystem.eventHandler.entity.CustomizeEvent;
import gradutionProject.IMUISystem.eventHandler.repository.RepositoryService;
import gradutionProject.IMUISystem.eventHandler.request.RestRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EventHandlerController {
    private final RepositoryService repositoryService;
    private final RestRequestService restRequestService;
    @GetMapping("/")
    public String home(){
        return "Hello! This is event handler!";
    }
    @GetMapping("/events/{eventName}")
    @ResponseStatus(HttpStatus.OK)
    public CustomizeEventDto getEvent(@PathVariable String eventName){
        CustomizeEvent event = repositoryService.getEvent(eventName);
        return CustomizeEventDto.builder()
                .eventName(event.getEventName())
                .description(event.getDescription())
                .variables(event.getVariables())
                .commConfigDto(restRequestService.getCommConfig(eventName))
                .notifyConfigDto(restRequestService.getNotifyConfig(eventName))
                .build();
    }
    @PostMapping("/events/new")
    @ResponseStatus(HttpStatus.CREATED)
    public void newEvent(@RequestBody CustomizeEventDto customizeEventDto){
        restRequestService.newCommConfig(customizeEventDto.getEventName(), customizeEventDto.getCommConfigDto());
        restRequestService.newNotifyConfig(customizeEventDto.getEventName(), customizeEventDto.getNotifyConfigDto());
        repositoryService.newEvent(CustomizeEvent.builder()
                .eventName(customizeEventDto.getEventName())
                .description(customizeEventDto.getDescription())
                .variables(customizeEventDto.getVariables())
                .build()
        );
    }
    @DeleteMapping("events/{eventName}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable String eventName){
        restRequestService.deleteCommConfig(eventName);
        restRequestService.deleteNotifyConfig(eventName);
        repositoryService.deleteEvent(eventName);
    }
}
