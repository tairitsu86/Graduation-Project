package gradutionProject.IMUISystem.eventHandler.controller;

import gradutionProject.IMUISystem.eventHandler.controller.exception.EventAlreadyExistException;
import gradutionProject.IMUISystem.eventHandler.controller.exception.EventNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIExceptionHandler {
    @ExceptionHandler(EventNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String exceptionHandler(EventNotExistException e){
        return e.toString();
    }
    @ExceptionHandler(EventAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String exceptionHandler(EventAlreadyExistException e){
        return e.toString();
    }
}
