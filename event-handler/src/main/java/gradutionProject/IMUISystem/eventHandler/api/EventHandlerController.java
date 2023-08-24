package gradutionProject.IMUISystem.eventHandler.api;

import gradutionProject.IMUISystem.eventHandler.dto.AlterEventDto;
import gradutionProject.IMUISystem.eventHandler.dto.NewEventDto;
import gradutionProject.IMUISystem.eventHandler.entity.CustomizeEvent;
import gradutionProject.IMUISystem.eventHandler.repository.RepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class EventHandlerController {
    private final RepositoryService repositoryService;
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
    public void newEvent(@RequestBody NewEventDto newEventDto){
        repositoryService.newEvent(CustomizeEvent.builder()
                .eventName(newEventDto.getEventName())
                .apiData(newEventDto.getApiData())
                .description(newEventDto.getDescription())
                .variables(newEventDto.getVariables())
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
    @PatchMapping("events/{eventName}/alter")
    @ResponseStatus(HttpStatus.OK)
    public void alterEvent(@PathVariable String eventName, @RequestBody AlterEventDto alterEventDto){
        repositoryService.alterEvent(
                eventName
                ,alterEventDto.getDescription()
                ,alterEventDto.getApiData()
                ,alterEventDto.getVariables()
        );
    }
    @DeleteMapping("events/{eventName}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable String eventName){
        repositoryService.deleteEvent(eventName);
    }
}
