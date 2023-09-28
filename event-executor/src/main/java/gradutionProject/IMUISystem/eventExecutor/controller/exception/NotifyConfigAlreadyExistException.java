package gradutionProject.IMUISystem.eventExecutor.controller.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NotifyConfigAlreadyExistException extends RuntimeException {
    private String eventName;
    @Override
    public String toString() {
        return String.format("The notify config of event:[%s] is already exist!",eventName);
    }
}
