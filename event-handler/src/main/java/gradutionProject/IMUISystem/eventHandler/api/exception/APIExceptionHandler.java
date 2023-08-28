package gradutionProject.IMUISystem.eventHandler.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIExceptionHandler {
    @ExceptionHandler(EventNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String eventNotExistExceptionHandler(EventNotExistException e){
        return e.toString();
    }
    @ExceptionHandler(EventAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String eventAlreadyExistExceptionHandler(EventAlreadyExistException e){
        return e.toString();
    }
}
