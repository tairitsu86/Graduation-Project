package gradutionProject.IMUISystem.eventExecutor.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import gradutionProject.IMUISystem.eventExecutor.dto.RespondConfigDto;
import gradutionProject.IMUISystem.eventExecutor.entity.CommConfig;
import gradutionProject.IMUISystem.eventExecutor.entity.RespondConfig;
import gradutionProject.IMUISystem.eventExecutor.repository.RepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EventHandlerController {
    private final RepositoryService repositoryService;
    private final ObjectMapper objectMapper;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String home(){
        System.out.println("Home!");
        return "Hello! This is event executor!";
    }

    @PostMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public String test(){
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

    @PostMapping("/events/{eventName}/respond/new")
    @ResponseStatus(HttpStatus.CREATED)
    public void newRespondConfig(@PathVariable String eventName,@RequestBody RespondConfigDto respondConfigDto){
        try {
            repositoryService.newRespondConfig(
                    RespondConfig.builder()
                            .eventName(eventName)
                            .respondType(respondConfigDto.getRespondType())
                            .respondData(objectMapper.writeValueAsString(respondConfigDto.getRespondData()))
                            .build()
            );
        } catch (JsonProcessingException e) {
            log.info("newRespondConfig error with: {}",e.getMessage(),e);
        }
    }

    @DeleteMapping("/events/{eventName}/respond/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRespondConfig(@PathVariable String eventName){
        repositoryService.deleteRespondConfig(eventName);
    }


}
