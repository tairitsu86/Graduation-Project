package graduationProject.loginSystem.groupManager.controller;

import graduationProject.loginSystem.groupManager.controller.exception.GroupAlreadyExistException;
import graduationProject.loginSystem.groupManager.controller.exception.GroupNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIExceptionHandler {
    @ExceptionHandler(GroupNotExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String exceptionHandler(GroupNotExistException e){
        return e.toString();
    }
    @ExceptionHandler(GroupAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String exceptionHandler(GroupAlreadyExistException e){
        return e.toString();
    }

}
