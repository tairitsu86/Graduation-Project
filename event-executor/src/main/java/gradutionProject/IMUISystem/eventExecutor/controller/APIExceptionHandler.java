package gradutionProject.IMUISystem.eventExecutor.controller;

import gradutionProject.IMUISystem.eventExecutor.controller.exception.CommConfigAlreadyExistException;
import gradutionProject.IMUISystem.eventExecutor.controller.exception.RespondConfigAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIExceptionHandler {
    @ExceptionHandler(CommConfigAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String exceptionHandler(CommConfigAlreadyExistException e){
        return e.toString();
    }

    @ExceptionHandler(RespondConfigAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String exceptionHandler(RespondConfigAlreadyExistException e){
        return e.toString();
    }
}
