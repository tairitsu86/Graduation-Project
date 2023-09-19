package gradutionProject.IoTSystem.deviceDatabase.api;

import gradutionProject.IoTSystem.deviceDatabase.api.exception.NoPermissionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIExceptionHandler {
    @ExceptionHandler(NoPermissionException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String eventNotExistExceptionHandler(NoPermissionException e){
        return e.toString();
    }
}
