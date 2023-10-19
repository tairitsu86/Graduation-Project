package graduationProject.IoTSystem.deviceDatabase.request;

import java.util.List;

public interface RestRequestService {
    List<String> getGroupsByUsername(String username);
}
