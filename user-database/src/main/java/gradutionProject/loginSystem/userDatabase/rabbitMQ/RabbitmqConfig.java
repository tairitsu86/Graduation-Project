package gradutionProject.loginSystem.userDatabase.rabbitMQ;

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
    public static final String loginEventQueue = "login-system/Login-event";
    static final String topicExchange = "user-database-exchange";
    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
    @Bean
    public Queue loginEventQueue() {
        return new Queue(loginEventQueue,false);
    }
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(topicExchange);
    }
    @Bean
    public Binding bindingLoginEventQueue(TopicExchange exchange) {
        return BindingBuilder.bind(loginEventQueue()).to(exchange).with(loginEventQueue);
    }
}
