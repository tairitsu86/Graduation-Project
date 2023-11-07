package graduationProject.IMIGSystem.testConsole.service;

import graduationProject.IMIGSystem.testConsole.controller.TestConsoleController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestConsoleService implements IMService {
    @Override
    public void SendTextMessage(String userId, String message) {
        TestConsoleController.show(false ,userId, message);
    }

}
