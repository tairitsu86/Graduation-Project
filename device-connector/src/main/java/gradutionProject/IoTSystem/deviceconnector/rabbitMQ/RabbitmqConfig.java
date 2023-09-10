package gradutionProject.IoTSystem.deviceconnector.rabbitMQ;

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
    public static final String DEVICE_STATE_QUEUE = "IoT/Device-state-event";
    public static final String DEVICE_CONTROL_QUEUE = "IoT/Device-control-event";
    public static final String MQ_EXCHANGE = "device-connector-exchange";
    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
    @Bean
    public Queue deviceStateQueue() {
        return new Queue(DEVICE_STATE_QUEUE,false);
    }
    @Bean
    public Queue deviceControlQueue() {
        return new Queue(DEVICE_CONTROL_QUEUE,false);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(MQ_EXCHANGE);
    }
    @Bean
    public Binding bindingUserEventQueue(TopicExchange exchange) {
        return BindingBuilder.bind(deviceStateQueue()).to(exchange).with(DEVICE_STATE_QUEUE);
    }
    @Bean
    public Binding bindingSendingEventQueue(TopicExchange exchange) {
        return BindingBuilder.bind(deviceControlQueue()).to(exchange).with(DEVICE_CONTROL_QUEUE);
    }
}
