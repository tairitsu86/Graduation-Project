package gradutionProject.IMUISystem.eventHandler.controller.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JsonMappingException extends RuntimeException{
    private String source;
    private String target;

    @Override
    public String toString() {
        return String.format("Source: [%s] mapping to target: [%s] fail!",source,target);
    }
}
