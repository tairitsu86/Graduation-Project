package graduationProject.IMUISystem.eventHandler.dto;

import graduationProject.IMUISystem.eventHandler.entity.IMUserData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStateDto {
    private IMUserData imUserData;
    private String username;
    private String eventName;
    private String description;
    private List<?> data;
    private Map<String,Object> parameters;
}
