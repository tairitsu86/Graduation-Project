package graduationProject.IMUISystem.eventExecutor.rabbitMQ;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitmqConfig {
    public static final String EXECUTE_EVENT_QUEUE = "IM-UI-System.Execute-Event";
    public static final String NEW_EVENT_QUEUE = "IM-UI-System.New-Event";
    public static final String SEND_MESSAGE_QUEUE = "IM-UI-System.Send-Message";
    public static final String NOTIFY_USER_QUEUE = "IM-UI-System.Notify-User";
    public static final String IMUI_RAE_EXCHANGE = "IM-UI-System_REST-API-event_Exchange";
    public static final String IMUI_IBC_EXCHANGE = "IM-UI-System_IM-base-communication_Exchange";
    public static final String SYS_NTF_EXCHANGE = "System_Notification_Exchange";
    public static final String SYS_SVC_EXCHANGE = "System_Service_Exchange";

    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean("IMUI_RAE")
    public TopicExchange exchangeIMUI_RAE() {
        return new TopicExchange(IMUI_RAE_EXCHANGE);
    }
    @Bean
    public Queue executeEventQueue() {
        return new Queue(EXECUTE_EVENT_QUEUE,false);
    }
    @Bean
    public Queue newEventQueue() {
        return new Queue(NEW_EVENT_QUEUE,false);
    }
    @Bean
    public Binding bindingExecuteEventQueue(@Qualifier("IMUI_RAE") TopicExchange exchange) {
        return BindingBuilder.bind(executeEventQueue()).to(exchange).with(EXECUTE_EVENT_QUEUE);
    }
    @Bean
    public Binding bindingNewEventQueue(@Qualifier("IMUI_RAE") TopicExchange exchange) {
        return BindingBuilder.bind(newEventQueue()).to(exchange).with(NEW_EVENT_QUEUE);
    }



    @Bean("IMUI_IBC")
    public TopicExchange exchangeIMUI_IBC() {
        return new TopicExchange(IMUI_IBC_EXCHANGE);
    }
    @Bean
    public Queue sendMessageQueue() {
        return new Queue(SEND_MESSAGE_QUEUE,false);
    }
    @Bean
    public Binding bindingSendMessageQueue(@Qualifier("IMUI_IBC") TopicExchange exchange) {
        return BindingBuilder.bind(sendMessageQueue()).to(exchange).with(SEND_MESSAGE_QUEUE);
    }



    @Bean("SYS_NTF")
    public FanoutExchange exchangeSYS_NTF() {
        return new FanoutExchange(SYS_NTF_EXCHANGE);
    }
    @Bean
    public Queue notifyUserQueue() {
        return new Queue(NOTIFY_USER_QUEUE,false);
    }
    @Bean
    public Binding bindingNotifyUserQueue(@Qualifier("SYS_NTF") FanoutExchange exchange) {
        return BindingBuilder.bind(notifyUserQueue()).to(exchange);
    }

    @Bean("SYS_SVC")
    public TopicExchange exchangeSYS_SVC() {
        return new TopicExchange(SYS_SVC_EXCHANGE);
    }
}
