package gradutionProject.IMUISystem.eventHandler.controller;

import gradutionProject.IMUISystem.eventHandler.controller.exception.EventAlreadyExistException;
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
    public CustomizeEvent getEvent(@PathVariable String eventName){
        return repositoryService.getEvent(eventName);
    }
    @PostMapping("/events/new")
    @ResponseStatus(HttpStatus.CREATED)
    public void newEvent(@RequestBody CustomizeEventDto customizeEventDto){
        restRequestService.newCommConfig(customizeEventDto.getCommConfigDto());
        restRequestService.newNotifyConfig(customizeEventDto.getNotifyConfigDto());
        repositoryService.newEvent(CustomizeEvent.builder()
                .eventName(customizeEventDto.getEventName())
                .description(customizeEventDto.getDescription())
                .variables(customizeEventDto.getVariables())
                .build()
        );
    }
    /*
    {
      "eventName": "event_name",
      "description": "description",
      "variables": [
        "variables1",
        "variables2"
      ],
      "apiData": {
        "urlTemplate": "url_template",
        "apiMethod": "GET",
        "requestBodyTemplate": "requestBodyTemplate"
      }
    }
    */
    @DeleteMapping("events/{eventName}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable String eventName){
        restRequestService.deleteCommConfig(eventName);
        restRequestService.deleteCommConfig(eventName);
        repositoryService.deleteEvent(eventName);
    }
}
