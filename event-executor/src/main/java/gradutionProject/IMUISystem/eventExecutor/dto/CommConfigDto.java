package gradutionProject.IMUISystem.eventExecutor.dto;

import gradutionProject.IMUISystem.eventExecutor.entity.CommConfig;
import gradutionProject.IMUISystem.eventExecutor.entity.MethodType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CommConfigDto {
    private MethodType methodType;
    private String url;
    private String body;
    public static CommConfigDto createCommConfigDto(CommConfig commConfig, Map<String, String> variables){
        String url = commConfig.getUrlTemplate();
        String body = commConfig.getBodyTemplate();
        if(body==null) body = "";
        if(variables!=null)
            for(String key:variables.keySet()){
                String replaceValue = String.format("${%s}",key);
                url = url.replace(replaceValue,variables.get(key));
                body = body.replace(replaceValue,variables.get(key));
            }
        return CommConfigDto.builder()
                .methodType(commConfig.getMethodType())
                .url(url)
                .body(body)
                .build();
    }
}
