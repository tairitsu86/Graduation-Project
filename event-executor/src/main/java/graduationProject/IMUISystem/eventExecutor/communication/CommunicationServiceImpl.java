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
            CommConfigDto commConfigDto = getCommConfigDto(commConfig, executeEventDto.getParameters());
            ResponseEntity<String> response = restRequestService.sendEventRequest(commConfigDto);

            if(!response.getStatusCode().is2xxSuccessful()){
                String message = String.format("Unexpected api error: \n%s", response.getBody());
                if(commConfigDto.getError().containsKey(response.getStatusCode().value()))
                    message = commConfigDto.getError().get(response.getStatusCode().value());
                respondService.respondTextMessage(executeEventDto.getExecutor(), message);
                log.info("API response code: {}", response.getStatusCode());
                return;
            }


            if (!repositoryService.isRespondConfigExist(executeEventDto.getEventName())) return;
            respondService.respond(executeEventDto.getExecutor(), repositoryService.getRespondConfig(executeEventDto.getEventName()), executeEventDto.getParameters(), response.getBody());
        }
    }


    public CommConfigDto getCommConfigDto(CommConfig commConfig, Map<String, Object> parameters){
        String url = commConfig.getUrlTemplate();
        String headerString = Objects.requireNonNullElse(commConfig.getHeaderTemplate(), "");
        String body = Objects.requireNonNullElse(commConfig.getBodyTemplate(), "");
        String errorString = Objects.requireNonNullElse(commConfig.getErrorTemplate(), "");

        if(parameters!=null)
            for(String s:parameters.keySet()){
                if(parameters.get(s)==null) continue;

                String replaceVariable = String.format("${%s}",s);
                String replaceValue = parameters.get(s).toString();
                url = url.replace(replaceVariable, replaceValue);
                headerString = headerString.replace(replaceVariable, replaceValue);
                errorString = errorString.replace(replaceVariable, replaceValue);

                if(s.startsWith("INT_")||s.startsWith("FLOAT_")||s.startsWith("BOOL_"))
                    replaceVariable = String.format("\"${%s}\"",s);
                body = body.replace(replaceVariable,parameters.get(s).toString());
            }
        Map<String, String> header;
        Map<Integer,String> error;
        try{
            header = objectMapper.readValue(headerString, new TypeReference<>() {});
            error = objectMapper.readValue(errorString, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return CommConfigDto.builder()
                .methodType(commConfig.getMethodType())
                .url(url)
                .header(header)
                .body(body)
                .error(error)
                .build();
    }
}
