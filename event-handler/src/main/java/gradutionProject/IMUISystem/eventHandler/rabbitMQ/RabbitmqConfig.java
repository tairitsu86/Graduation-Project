package gradutionProject.IMUISystem.eventHandler.rabbitMQ;

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
    public static final String userEventQueue = "IM-UI/User-event";
    public static final String sendingEventQueue = "IM-UI/Sending-event";
    static final String topicExchangeName = "event-handler-exchange";
    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
    @Bean
    public Queue userEventQueue() {
        return new Queue(userEventQueue,false);
    }
    @Bean
    public Queue sendingEventQueue() {
        return new Queue(sendingEventQueue,false);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }
    @Bean
    public Binding bindingUserEventQueue(TopicExchange exchange) {
        return BindingBuilder.bind(userEventQueue()).to(exchange).with(userEventQueue);
    }
    @Bean
    public Binding bindingSendingEventQueue(TopicExchange exchange) {
        return BindingBuilder.bind(sendingEventQueue()).to(exchange).with(sendingEventQueue);
    }
}
