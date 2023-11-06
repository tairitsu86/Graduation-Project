package graduationProject.IMIGSystem.testConsole.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestConsoleService implements IMService {
    @Override
    public void SendTextMessage(String userId, String message) {
        log.info("System to user[{}]: {}", userId, message);
    }

}
