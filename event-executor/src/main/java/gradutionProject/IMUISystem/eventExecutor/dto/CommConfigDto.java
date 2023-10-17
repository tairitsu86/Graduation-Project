package gradutionProject.IMUISystem.eventExecutor.dto;

import gradutionProject.IMUISystem.eventExecutor.entity.CommConfig;
import gradutionProject.IMUISystem.eventExecutor.entity.MethodType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CommConfigDto {
    private MethodType methodType;
    private String url;
    private String header;
    private String body;
    public static CommConfigDto createCommConfigDto(CommConfig commConfig, Map<String, String> variables){
        String url = commConfig.getUrlTemplate();
        String header = commConfig.getHeaderTemplate();
        if(header==null) header = "";
        String body = commConfig.getBodyTemplate();
        if(body==null) body = "";
        if(variables!=null)
            for(String key:variables.keySet()){
                String replaceValue = String.format("${%s}",key);
                url = url.replace(replaceValue,variables.get(key));
                header = header.replace(replaceValue,variables.get(key));
                body = body.replace(replaceValue,variables.get(key));
            }
        return CommConfigDto.builder()
                .methodType(commConfig.getMethodType())
                .url(url)
                .header(header)
                .body(body)
                .build();
    }
}
