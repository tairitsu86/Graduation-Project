package gradutionProject.IMUISystem.loginTracker.rabbitMQ;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitmqConfig {
    public static final String LOGIN_LOG_QUEUE = "Login-System.Login-Log";
    public static final String LOGOUT_LOG_QUEUE = "Login-System.Logout-Log";
    static final String LOGIN_UA_EXCHANGE = "Login-System_User-Access_Exchange";
    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }



    @Bean("LOGIN_UA")
    public TopicExchange exchangeLOGIN_UA() {
        return new TopicExchange(LOGIN_UA_EXCHANGE);
    }
    @Bean
    public Queue loginLogQueue() {
        return new Queue(LOGIN_LOG_QUEUE,false);
    }
    @Bean
    public Queue logoutLogQueue() {
        return new Queue(LOGOUT_LOG_QUEUE,false);
    }
    @Bean
    public Binding bindingLoginLogQueue(@Qualifier("LOGIN_UA") TopicExchange exchange) {
        return BindingBuilder.bind(loginLogQueue()).to(exchange).with(LOGIN_LOG_QUEUE);
    }
    @Bean
    public Binding bindingLogoutLogQueue(@Qualifier("LOGIN_UA") TopicExchange exchange) {
        return BindingBuilder.bind(logoutLogQueue()).to(exchange).with(LOGOUT_LOG_QUEUE);
    }
}
