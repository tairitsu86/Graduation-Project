package graduationProject.IMUISystem.eventExecutor.respond;

import graduationProject.IMUISystem.eventExecutor.entity.RespondConfig;

import java.util.Map;

public interface RespondService {
    void respond(String username, RespondConfig respondConfig, Map<String, Object> parameters, String jsonData);
}
