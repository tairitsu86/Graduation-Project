package gradutionProject.IMUISystem.eventHandler.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Component
public class Option {
    private String displayName;
    private String nextEvent;
    private Map<String,String> optionParameters;

    private final ObjectMapper objectMapper;
    public String tranToString(Option o) {
        try {
            return objectMapper.writeValueAsString(o);
        }catch (JsonProcessingException e){
            return null;
        }
    }
    public Option tranFromString(String s){
        try {
            return objectMapper.readValue(s,Option.class);
        }catch (JsonProcessingException e){
            return null;
        }
    }
    public List<String> tranToStringList(List<Option> o){
        List<String> options = new ArrayList<>();
        try{
            for (int i=0;i<o.size();i++)
                options.add(objectMapper.writeValueAsString(o.get(i)));
        }catch (JsonProcessingException e){
            return null;
        }
        return options;
    }
}
