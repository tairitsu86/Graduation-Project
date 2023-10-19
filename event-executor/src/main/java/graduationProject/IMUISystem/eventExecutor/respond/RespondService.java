package graduationProject.IMUISystem.eventExecutor.respond;

import graduationProject.IMUISystem.eventExecutor.entity.RespondConfig;

public interface RespondService {
    void respond(String username, RespondConfig respondConfig, String jsonData);
}
