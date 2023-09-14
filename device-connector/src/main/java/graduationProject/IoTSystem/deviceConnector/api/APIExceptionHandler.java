package graduationProject.IoTSystem.deviceConnector.api;

import graduationProject.IoTSystem.deviceConnector.api.exception.DeviceStateNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIExceptionHandler {
    @ExceptionHandler(DeviceStateNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String eventNotExistExceptionHandler(DeviceStateNotFoundException e){
        return e.toString();
    }
}
