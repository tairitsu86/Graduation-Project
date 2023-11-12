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
public class NewCommConfigDto {
    private MethodType methodType;
    private String urlTemplate;
    private Map<String,String> headerTemplate;
    private Object bodyTemplate;
    private Map<Integer, String> errorTemplate;
}
