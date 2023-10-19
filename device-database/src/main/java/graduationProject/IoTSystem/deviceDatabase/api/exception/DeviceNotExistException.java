package graduationProject.IoTSystem.deviceDatabase.api.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeviceNotExistException extends RuntimeException{
    private String deviceId;

    @Override
    public String toString() {
        return String.format("Device with id: [%s] not exist!",deviceId);
    }
}
