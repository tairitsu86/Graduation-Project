package gradutionProject.IMUISystem.messageSender.instantMessagingPlatform.line;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import gradutionProject.IMUISystem.messageSender.instantMessagingPlatform.IMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
@Service
public class LineService implements IMService {
    @Autowired
    private LineMessagingClient lineMessagingClient;
    @Override
    public void SendTextMessage(String userId, String message) {
        TextMessage textMessage = new TextMessage(message);
        pushMessage(userId,textMessage);
    }
    public void pushMessage(String userId, Message message){
        PushMessage pushMessage = new PushMessage(userId, message);
        System.out.println(pushMessage.getTo()+"\n"+pushMessage.getMessages());
        BotApiResponse response = null;
        try {
            response = lineMessagingClient.pushMessage(pushMessage).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
