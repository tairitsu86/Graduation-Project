package gradutionproject.loginsystem.userdatabase.api;

import gradutionproject.loginsystem.userdatabase.api.excpetion.UserNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class APIExceptionHandler {
    @ExceptionHandler(UserNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String userNotExistExceptionHandler(UserNotExistException e){
        String errorMessage = e.toString();
        return errorMessage;
    }

}
