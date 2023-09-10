package gradutionProject.IoTSystem.deviceconnector.api.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeviceNotFoundException extends RuntimeException{
    private String device_id;

    @Override
    public String toString() {
        return String.format("Device id [%s] not found!",device_id);
    }
}
