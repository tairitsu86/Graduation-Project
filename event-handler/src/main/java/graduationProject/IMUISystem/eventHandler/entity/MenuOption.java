package graduationProject.IMUISystem.eventHandler.entity;

import lombok.*;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuOption {
    private String displayName;
    private String nextEvent;
    private Map<String,Object> optionParameters;
}
