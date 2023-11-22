package graduationProject.IMUISystem.eventExecutor.dto;

import graduationProject.IMUISystem.eventExecutor.entity.MethodType;
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
    private Map<String, String> header;
    private String body;
    private Map<String, String> error;
}
