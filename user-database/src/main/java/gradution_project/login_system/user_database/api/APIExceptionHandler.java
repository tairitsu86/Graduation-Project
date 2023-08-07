package gradution_project.login_system.user_database.api;

import gradution_project.login_system.user_database.api.excpetion.UserNotExistException;
import gradution_project.login_system.user_database.api.excpetion.UsernameAlreadyExistException;
import gradution_project.login_system.user_database.api.excpetion.UserLoginWithIncorrectAccountException;
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
