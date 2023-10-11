package gradutionProject.IMUISystem.eventHandler.controller.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EventAlreadyExistException extends RuntimeException{
    private String eventName;

    @Override
    public String toString() {
        return String.format("Event named [%s] is already exist!",eventName);
    }
}
