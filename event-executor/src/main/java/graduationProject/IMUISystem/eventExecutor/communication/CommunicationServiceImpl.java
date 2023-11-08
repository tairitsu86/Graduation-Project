package graduationProject.IMUISystem.eventExecutor.communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import graduationProject.IMUISystem.eventExecutor.dto.CommConfigDto;
import graduationProject.IMUISystem.eventExecutor.dto.ExecuteEventDto;
import graduationProject.IMUISystem.eventExecutor.entity.CommConfig;
import graduationProject.IMUISystem.eventExecutor.entity.MethodType;
import graduationProject.IMUISystem.eventExecutor.rabbitMQ.MQEventPublisher;
import graduationProject.IMUISystem.eventExecutor.repository.RepositoryService;
import graduationProject.IMUISystem.eventExecutor.request.RestRequestService;
import graduationProject.IMUISystem.eventExecutor.respond.RespondService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Slf4j
public class CommunicationServiceImpl implements CommunicationService{
    private final RepositoryService repositoryService;
    private final RestRequestService restRequestService;
    private final MQEventPublisher mqEventPublisher;
    private final RespondService respondService;
    private final ObjectMapper objectMapper;

    @Override
    public void handleExecuteEvent(ExecuteEventDto executeEventDto) {
        if(!repositoryService.isCommConfigExist(executeEventDto.getEventName())){
            log.info("commConfig of event [{}] not exist!", executeEventDto.getEventName());
            if (!repositoryService.isRespondConfigExist(executeEventDto.getEventName())) return;
            respondService.respond(executeEventDto.getExecutor(), repositoryService.getRespondConfig(executeEventDto.getEventName()), executeEventDto.getParameters(), "");
            return;
        }

        CommConfig commConfig = repositoryService.getCommConfig(executeEventDto.getEventName());
        if (Objects.requireNonNull(commConfig.getMethodType()) == MethodType.MQ) {
            mqEventPublisher.publishCustomEvent(getCommConfigDto(commConfig, executeEventDto.getParameters()));
        } else {
            ResponseEntity<String> response =
                    restRequestService.sendEventRequest(getCommConfigDto(commConfig, executeEventDto.getParameters()));

            if(!response.getStatusCode().is2xxSuccessful()){
                String message;
                if(response.getStatusCode().is4xxClientError()){
                    message = String.format("%s", response.getBody());
                }else if(response.getStatusCode().is5xxServerError()){
                    message = String.format("System error: %s", response.getBody());
                }else{
                    message = String.format("Unknown error: %s\n%s", response.getStatusCode(), response.getBody());
                }
                log.info("API response code not 2xx, with status code: {}, with message: {}, respond to user : {}", response.getStatusCode(), response.getBody(), message);
                respondService.respondTextMessage(executeEventDto.getExecutor(), message);
                return;
            }


            if (!repositoryService.isRespondConfigExist(executeEventDto.getEventName())) return;
            respondService.respond(executeEventDto.getExecutor(), repositoryService.getRespondConfig(executeEventDto.getEventName()), executeEventDto.getParameters(), response.getBody());
        }
    }


    public CommConfigDto getCommConfigDto(CommConfig commConfig, Map<String, Object> parameters){
        String url = commConfig.getUrlTemplate();
        String headerString = commConfig.getHeaderTemplate();
        if(headerString==null) headerString = "";
        String body = commConfig.getBodyTemplate();
        if(body==null) body = "";
        if(parameters!=null)
            for(String s:parameters.keySet()){
                if(parameters.get(s)==null) continue;

                String replaceValue = String.format("${%s}",s);
                url = url.replace(replaceValue,parameters.get(s).toString());
                headerString = headerString.replace(replaceValue,parameters.get(s).toString());

                if(s.startsWith("INT_")||s.startsWith("FLOAT_")||s.startsWith("BOOL_"))
                    replaceValue = String.format("\"${%s}\"",s);
                body = body.replace(replaceValue,parameters.get(s).toString());
            }
        Map<String, String> header;
        try{
            header = objectMapper.readValue(headerString, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return CommConfigDto.builder()
                .methodType(commConfig.getMethodType())
                .url(url)
                .header(header)
                .body(body)
                .build();
    }
}
