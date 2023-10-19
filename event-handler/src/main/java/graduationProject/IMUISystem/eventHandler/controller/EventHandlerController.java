package graduationProject.IMUISystem.eventHandler.controller;

import graduationProject.IMUISystem.eventHandler.dto.CustomizeEventDto;
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
    public String test(@RequestBody Map<String,Object> data){
        log.info("test data: {}", data);
        return "{\n" +
                "  \"single\": \"Hi\",\n" +
                "  \"array\": [\n" +
                "    {\n" +
                "      \"c1\": \"11\",\n" +
                "      \"c2\": \"12\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"c1\": \"21\",\n" +
                "      \"c2\": \"22\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"c1\": \"31\",\n" +
                "      \"c2\": \"32\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"c1\": \"41\",\n" +
                "      \"c2\": \"42\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
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

}
