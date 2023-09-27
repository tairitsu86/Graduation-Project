package gradutionProject.IMUISystem.eventHandler.controller.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class HttpApiException extends RuntimeException {
    private String message;

    @Override
    public String toString() {
        return String.format("API Request have error with message: [%s]",message);
    }
}
