package gradutionProject.loginSystem.userDatabase.api;

import gradutionProject.loginSystem.userDatabase.api.excpetion.UserNotExistException;
import gradutionProject.loginSystem.userDatabase.api.excpetion.UsernameAlreadyExistException;
import gradutionProject.loginSystem.userDatabase.api.excpetion.UserLoginWithIncorrectAccountException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIExceptionHandler {
    @ExceptionHandler(UserNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String userNotExistExceptionHandler(UserNotExistException e){
        return e.toString();
    }

    @ExceptionHandler(UsernameAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String usernameAlreadyExistExceptionHandler(UsernameAlreadyExistException e){
        return e.toString();
    }

    @ExceptionHandler(UserLoginWithIncorrectAccountException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String userLoginWithIncorrectAccountExceptionHandler(UserLoginWithIncorrectAccountException e){
        return e.toString();
    }

}
