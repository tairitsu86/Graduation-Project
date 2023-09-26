package gradutionProject.IMUISystem.eventExecutor.controller.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CommConfigAlreadyExistException extends RuntimeException{
    private String eventName;
    @Override
    public String toString() {
        return String.format("The comm config of event:[%s] is already exist!",eventName);
    }
}
