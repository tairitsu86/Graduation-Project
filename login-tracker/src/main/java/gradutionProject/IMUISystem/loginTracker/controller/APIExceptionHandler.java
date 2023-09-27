package gradutionProject.IMUISystem.loginTracker.controller;

import gradutionProject.IMUISystem.loginTracker.controller.exception.LoginUserNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIExceptionHandler {
    @ExceptionHandler(LoginUserNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String loginUserNotExistException(LoginUserNotExistException e){
        return e.toString();
    }
}
