package gradutionProject.IMUISystem.eventHandler.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class Option {
    private String displayName;
    private String nextEvent;
    private Map<String,String> optionParameters;

    @JsonIgnoreProperties
    //Use final injection will let it can't find 0 args constructor, then go wrong.
    //This error only happen when final injection(with @RequiredArgsConstructor/ Constructor with contains all final field)
    //and @builder(with @AllArgsConstructor) both exist, and we want creating a bean by @Component.
    //But use @Autowired didn't, why?
    @Autowired
    private ObjectMapper objectMapper;

    public Option(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }
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
