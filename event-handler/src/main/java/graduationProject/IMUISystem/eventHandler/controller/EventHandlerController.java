package graduationProject.IMUISystem.eventHandler.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import graduationProject.IMUISystem.eventHandler.dto.CustomizeEventDto;
import graduationProject.IMUISystem.eventHandler.entity.MenuOption;
import graduationProject.IMUISystem.eventHandler.repository.RepositoryService;
import graduationProject.IMUISystem.eventHandler.request.RestRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @PostMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public String test(@RequestHeader String testHeader, @RequestBody Object o) throws JsonProcessingException {
        log.info("test header:{}",testHeader);
        log.info("test body:{}",new ObjectMapper().writeValueAsString(o));
        return "{\n" +
                "  \"single\": \"Hi\",\n" +
                "  \"array\": [\n" +
                "    {\n" +
                "      \"c1\": \"11\",\n" +
                "      \"c2\": \"12\",\n" +
                "      \"user\": \"OMO\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"c1\": \"21\",\n" +
                "      \"c2\": \"22\",\n" +
                "      \"user\": \"OVO\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"c1\": \"31\",\n" +
                "      \"c2\": \"32\",\n" +
                "      \"user\": \"OWO\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"c1\": \"41\",\n" +
                "      \"c2\": \"42\",\n" +
                "      \"user\": \"OUO\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"bool\": false,\n" +
                "  \"boolArray\": [\n" +
                "    {\n" +
                "      \"c1\": true,\n" +
                "      \"group\": \"group1\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"c1\": false,\n" +
                "      \"group\": \"group2\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"int\": 100,\n" +
                "  \"intArray\": [\n" +
                "    {\n" +
                "      \"c1\": \"123\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"c1\": \"456\"\n" +
                "    }\n" +
                "  ]\n" +
                "}\n";
    }

    @GetMapping("/events/{eventName}")
    @ResponseStatus(HttpStatus.OK)
    public CustomizeEventDto getEvent(@PathVariable String eventName){
        CustomizeEventDto event = repositoryService.getEventDto(eventName);
        event.setCommConfigDto(restRequestService.getCommConfig(eventName));
        event.setRespondConfigDto(restRequestService.getRespondConfig(eventName));
        return event;
    }
    @PostMapping("/events/new")
    @ResponseStatus(HttpStatus.CREATED)
    public void newEvent(@RequestBody CustomizeEventDto customizeEventDto){
        restRequestService.newCommConfig(customizeEventDto.getEventName(), customizeEventDto.getCommConfigDto());
        restRequestService.newRespondConfig(customizeEventDto.getEventName(), customizeEventDto.getRespondConfigDto());
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
