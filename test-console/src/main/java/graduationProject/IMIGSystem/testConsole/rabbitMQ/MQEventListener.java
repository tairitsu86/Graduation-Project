package graduationProject.IMIGSystem.testConsole.rabbitMQ;

import graduationProject.IMIGSystem.testConsole.dto.SendingServiceDto;
import graduationProject.IMIGSystem.testConsole.service.IMService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static graduationProject.IMIGSystem.testConsole.rabbitMQ.RabbitmqConfig.TEST_QUEUE;

@Slf4j
@RequiredArgsConstructor
@Component
public class MQEventListener {
    private final IMService imService;
    @RabbitListener(queues={TEST_QUEUE})
    public void receive(SendingServiceDto sendingServiceDto) {
        try {
            log.info("Sending Service: {}",sendingServiceDto);
            for (String userId:sendingServiceDto.getUserIdList())
                imService.SendTextMessage(userId, sendingServiceDto.getMessage());
        }catch (Exception e){
            log.info("Something go wrong: {}",e.getMessage(),e);
        }
    }
}
