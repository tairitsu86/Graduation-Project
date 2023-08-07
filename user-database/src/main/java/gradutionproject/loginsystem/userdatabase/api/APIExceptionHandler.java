package gradutionproject.loginsystem.userdatabase.api;

import gradutionproject.loginsystem.userdatabase.api.excpetion.UserLoginWithIncorrectAccountException;
import gradutionproject.loginsystem.userdatabase.api.excpetion.UserNotExistException;
import gradutionproject.loginsystem.userdatabase.api.excpetion.UsernameAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class APIExceptionHandler {
    private String errorMessage;
    @ExceptionHandler(UserNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String userNotExistExceptionHandler(UserNotExistException e){
        return (errorMessage = e.toString());
    }

    @ExceptionHandler(UsernameAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String userNotExistExceptionHandler(UsernameAlreadyExistException e){
        return (errorMessage = e.toString());
    }

    @ExceptionHandler(UserLoginWithIncorrectAccountException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String userLoginErrorExceptionHandler(UserLoginWithIncorrectAccountException e){
        return (errorMessage = e.toString());
    }

}
