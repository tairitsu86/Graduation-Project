package graduationProject.IMUISystem.eventExecutor.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import graduationProject.IMUISystem.eventExecutor.dto.NewCommConfigDto;
import graduationProject.IMUISystem.eventExecutor.entity.CommConfig;
import graduationProject.IMUISystem.eventExecutor.entity.RespondConfig;
import graduationProject.IMUISystem.eventExecutor.controller.exception.CommConfigAlreadyExistException;
import graduationProject.IMUISystem.eventExecutor.controller.exception.RespondConfigAlreadyExistException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RepositoryServiceImpl implements RepositoryService{
    private final CommConfigRepository commConfigRepository;
    private final RespondConfigRepository respondConfigRepository;
    private final ObjectMapper objectMapper;
    @PostConstruct
    public void init() {

    }

    @Override
    public void newCommConfig(String eventName, NewCommConfigDto newCommConfigDto) {
        if(commConfigRepository.existsById(eventName))
            throw new CommConfigAlreadyExistException(eventName);
        String headerTemplate, bodyTemplate;
        try {
            headerTemplate = objectMapper.writeValueAsString(newCommConfigDto.getHeaderTemplate());
            bodyTemplate = objectMapper.writeValueAsString(newCommConfigDto.getBodyTemplate());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        commConfigRepository.save(
                CommConfig.builder()
                        .eventName(eventName)
                        .urlTemplate(newCommConfigDto.getUrlTemplate())
                        .methodType(newCommConfigDto.getMethodType())
                        .headerTemplate(headerTemplate)
                        .bodyTemplate(bodyTemplate)
                        .build()
        );
    }

    @Override
    public void deleteCommConfig(String eventName) {
        commConfigRepository.deleteById(eventName);
    }

    @Override
    public boolean isCommConfigExist(String eventName) {
        return commConfigRepository.existsById(eventName);
    }

    @Override
    public CommConfig getCommConfig(String eventName) {
        if(!commConfigRepository.existsById(eventName)) throw new RuntimeException("Event not exist");
        return commConfigRepository.getReferenceById(eventName);
    }

    @Override
    public NewCommConfigDto getNewCommConfigDto(String eventName) {
        CommConfig commConfig = getCommConfig(eventName);
        Map<String,String> headerTemplate;
        Object bodyTemplate;
        try {
            headerTemplate = objectMapper.readValue(commConfig.getHeaderTemplate(), new TypeReference<>() {});
            bodyTemplate = objectMapper.readValue(commConfig.getBodyTemplate(),Object.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return NewCommConfigDto.builder()
                .urlTemplate(commConfig.getUrlTemplate())
                .methodType(commConfig.getMethodType())
                .headerTemplate(headerTemplate)
                .bodyTemplate(bodyTemplate)
                .build();
    }

    @Override
    public void newRespondConfig(RespondConfig respondConfig) {
        if(respondConfigRepository.existsById(respondConfig.getEventName()))
            throw new RespondConfigAlreadyExistException(respondConfig.getEventName());
        respondConfigRepository.save(respondConfig);
    }

    @Override
    public void deleteRespondConfig(String eventName) {
        respondConfigRepository.deleteById(eventName);
    }

    @Override
    public boolean isRespondConfigExist(String eventName) {
        return respondConfigRepository.existsById(eventName);
    }

    @Override
    public RespondConfig getRespondConfig(String eventName) {
        if(!respondConfigRepository.existsById(eventName)) return null;
        return respondConfigRepository.getReferenceById(eventName);
    }
}
