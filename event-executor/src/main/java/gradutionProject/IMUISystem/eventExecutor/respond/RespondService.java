package gradutionProject.IMUISystem.eventExecutor.respond;

import gradutionProject.IMUISystem.eventExecutor.entity.RespondConfig;

public interface RespondService {
    void respond(String username, RespondConfig respondConfig, String jsonData);
}
