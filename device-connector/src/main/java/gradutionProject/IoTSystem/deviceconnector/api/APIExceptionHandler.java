package gradutionProject.IoTSystem.deviceconnector.api;

import gradutionProject.IoTSystem.deviceconnector.api.exception.DeviceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIExceptionHandler {
    @ExceptionHandler(DeviceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String eventNotExistExceptionHandler(DeviceNotFoundException e){
        return e.toString();
    }
}
