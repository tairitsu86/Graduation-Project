package graduationProject.IMUISystem.eventExecutor.controller.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RespondConfigAlreadyExistException extends RuntimeException {
    private String eventName;
    @Override
    public String toString() {
        return String.format("The respond config of event:[%s] is already exist!",eventName);
    }
}
