package graduationProject.IoTSystem.deviceConnector.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RepositoryServiceImpl implements RepositoryService{
    private final DeviceRepository deviceRepository;
    private final DeviceStateHistoryRepository deviceStateHistoryRepository;

}
