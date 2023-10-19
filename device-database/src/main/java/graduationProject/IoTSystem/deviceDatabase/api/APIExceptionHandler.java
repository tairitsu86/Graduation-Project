package graduationProject.IoTSystem.deviceDatabase.api;

import graduationProject.IoTSystem.deviceDatabase.api.exception.DeviceNotExistException;
import graduationProject.IoTSystem.deviceDatabase.api.exception.NoPermissionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIExceptionHandler {
    @ExceptionHandler(NoPermissionException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String exceptionHandler(NoPermissionException e){
        return e.toString();
    }
    @ExceptionHandler(DeviceNotExistException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String exceptionHandler(DeviceNotExistException e){
        return e.toString();
    }
}
