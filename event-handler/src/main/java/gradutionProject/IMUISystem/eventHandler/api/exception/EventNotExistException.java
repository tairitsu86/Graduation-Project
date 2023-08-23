package gradutionProject.IMUISystem.eventHandler.api.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EventNotExistException extends RuntimeException{
    private String eventName;
    @Override
    public String toString() {
        return String.format("Event named [%s] is not exist!",eventName);
    }
}
