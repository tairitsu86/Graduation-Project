package graduationProject.IMIGSystem.lineService.rabbitMQ;

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
    public static final String IM_USER_MESSAGE_QUEUE = "IM-UI-System.IM-User-Message";
    public static final String LINE_QUEUE = "IM-Integration-System.LINE";
    public static final String IMUI_IBC_EXCHANGE = "IM-UI-System_IM-base-communication_Exchange";
    public static final String IMIG_IPS_EXCHANGE = "IM-Integration-System_IM-platform-service_Exchange";

    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }



    @Bean("IMUI_IBC")
    public TopicExchange exchangeIMUI_IBC() {
        return new TopicExchange(IMUI_IBC_EXCHANGE);
    }
    @Bean
    public Queue imUserMessageQueue() {
        return new Queue(IM_USER_MESSAGE_QUEUE,false);
    }
    @Bean
    public Binding bindingIMUSerMessage(@Qualifier("IMUI_IBC") TopicExchange exchange) {
        return BindingBuilder.bind(imUserMessageQueue()).to(exchange).with(IM_USER_MESSAGE_QUEUE);
    }

    @Bean("IMIG_IPS")
    public TopicExchange exchangeIMIG_IPS() {
        return new TopicExchange(IMIG_IPS_EXCHANGE);
    }
    @Bean
    public Queue lineQueue() {
        return new Queue(LINE_QUEUE,false);
    }
    @Bean
    public Binding bindingLineQueue(@Qualifier("IMIG_IPS") TopicExchange exchange) {
        return BindingBuilder.bind(lineQueue()).to(exchange).with(LINE_QUEUE);
    }

}
