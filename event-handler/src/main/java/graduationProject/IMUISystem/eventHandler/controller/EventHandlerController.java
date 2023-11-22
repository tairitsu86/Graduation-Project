package graduationProject.IMUISystem.eventHandler.controller;

import graduationProject.IMUISystem.eventHandler.dto.CustomizeEventDto;
import graduationProject.IMUISystem.eventHandler.entity.MenuOption;
import graduationProject.IMUISystem.eventHandler.repository.RepositoryService;
import graduationProject.IMUISystem.eventHandler.request.RestRequestService;
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
        CustomizeEventDto event = repositoryService.getEventDto(eventName);
        event.setCommConfig(restRequestService.getCommConfig(eventName));
        event.setRespondConfig(restRequestService.getRespondConfig(eventName));
        return event;
    }
    @PostMapping("/events/new")
    @ResponseStatus(HttpStatus.CREATED)
    public void newEvent(@RequestBody CustomizeEventDto customizeEventDto){
        restRequestService.newCommConfig(customizeEventDto.getEventName(), customizeEventDto.getCommConfig());
        restRequestService.newRespondConfig(customizeEventDto.getEventName(), customizeEventDto.getRespondConfig());
        repositoryService.newEvent(customizeEventDto);
    }
    @DeleteMapping("events/{eventName}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable String eventName){
        restRequestService.deleteCommConfig(eventName);
        restRequestService.deleteRespondConfig(eventName);
        repositoryService.deleteEvent(eventName);
    }

    @PatchMapping("menus/default/options/add")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addOption(@RequestBody MenuOption option){
        repositoryService.addOptionToDefaultMenu(option);
    }

    @PatchMapping("menus/default/options/remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeOption(@RequestBody MenuOption option){
        repositoryService.removeOptionFromDefaultMenu(option);
    }

}
