package graduationProject.IoTSystem.deviceConnector.controller.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeviceIdNotExistException extends RuntimeException{
    private String deviceId;

    @Override
    public String toString() {
        return String.format("Device id: [%s] not exist!", deviceId);
    }
}
