package graduationProject.IoTSystem.deviceDatabase.api.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NoPermissionException extends RuntimeException{
    private String username;
    private String deviceId;
    @Override
    public String toString() {
        return String.format("User [%s] don't have permission with device [%s]!",username,deviceId);
    }
}
