package graduationProject.IoTSystem.deviceConnector.controller.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeviceStateNotFoundException extends RuntimeException{
    private String deviceId;
    private String stateName;
    @Override
    public String toString() {
        return String.format("The state [%s] with device id [%s] is not found!",stateName,deviceId);
    }
}
