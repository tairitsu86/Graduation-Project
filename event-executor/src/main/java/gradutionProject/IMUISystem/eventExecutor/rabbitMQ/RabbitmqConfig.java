package gradutionProject.IMUISystem.eventExecutor.rabbitMQ;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitmqConfig {
    public static final String EXECUTE_EVENT_QUEUE = "IM-UI/Execute-event";
    public static final String SENDING_EVENT_QUEUE = "IM-UI/Sending-event";
    public static final String DEVICE_STATE_QUEUE = "IoT/Device-state-event";
    public static final String MQ_EXCHANGE = "event-executor-exchange";

    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
    @Bean
    public Queue executeEventQueue() {
        return new Queue(EXECUTE_EVENT_QUEUE,false);
    }
    @Bean
    public Queue sendingEventQueue() {
        return new Queue(SENDING_EVENT_QUEUE,false);
    }
    @Bean
    public Queue deviceStateQueue() {
        return new Queue(DEVICE_STATE_QUEUE,false);
    }
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(MQ_EXCHANGE);
    }
    @Bean
    public Binding bindingExecuteEventQueue(TopicExchange exchange) {
        return BindingBuilder.bind(executeEventQueue()).to(exchange).with(EXECUTE_EVENT_QUEUE);
    }
    @Bean
    public Binding bindingSendingEventQueue(TopicExchange exchange) {
        return BindingBuilder.bind(sendingEventQueue()).to(exchange).with(SENDING_EVENT_QUEUE);
    }
    @Bean
    public Binding bindingDeviceStateQueue(TopicExchange exchange) {
        return BindingBuilder.bind(deviceStateQueue()).to(exchange).with(DEVICE_STATE_QUEUE);
    }

}
