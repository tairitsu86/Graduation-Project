package graduationProject.IMUISystem.eventExecutor.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import graduationProject.IMUISystem.eventExecutor.dto.NewCommConfigDto;
import graduationProject.IMUISystem.eventExecutor.entity.RespondConfig;
import graduationProject.IMUISystem.eventExecutor.repository.RepositoryService;
import graduationProject.IMUISystem.eventExecutor.dto.RespondConfigDto;
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
    public String test(@RequestHeader String testHeader, @RequestBody Object o) throws JsonProcessingException {
        log.info("test header:{}",testHeader);
        log.info("test body:{}",objectMapper.writeValueAsString(o));
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

    @GetMapping("/events/{eventName}/comm")
    @ResponseStatus(HttpStatus.OK)
    public NewCommConfigDto getCommConfig(@PathVariable String eventName){
        return repositoryService.getNewCommConfigDto(eventName);
    }

    @PostMapping("/events/{eventName}/comm/new")
    @ResponseStatus(HttpStatus.CREATED)
    public void newCommConfig(@PathVariable String eventName, @RequestBody NewCommConfigDto newCommConfigDto){
        repositoryService.newCommConfig(eventName, newCommConfigDto);
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
