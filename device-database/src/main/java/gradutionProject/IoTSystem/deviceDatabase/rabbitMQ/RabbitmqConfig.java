package gradutionProject.IoTSystem.deviceDatabase.rabbitMQ;

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
    public static final String DEVICE_INFO_QUEUE = "IoT/Device-info-event";
    public static final String USER_CONTROL_QUEUE = "IoT/User-control-event";
    public static final String DEVICE_CONTROL_QUEUE = "IoT/Device-control-event";
    public static final String MQ_EXCHANGE = "device-database-exchange";
    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
    @Bean
    public Queue deviceInfoQueue() {
        return new Queue(DEVICE_INFO_QUEUE,false);
    }
    @Bean
    public Queue userControlQueue() {
        return new Queue(USER_CONTROL_QUEUE,false);
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
    public Binding bindingDeviceInfoQueue(TopicExchange exchange) {
        return BindingBuilder.bind(deviceInfoQueue()).to(exchange).with(DEVICE_INFO_QUEUE);
    }
    @Bean
    public Binding bindingUserControlQueue(TopicExchange exchange) {
        return BindingBuilder.bind(userControlQueue()).to(exchange).with(USER_CONTROL_QUEUE);
    }
    @Bean
    public Binding bindingDeviceControlQueue(TopicExchange exchange) {
        return BindingBuilder.bind(deviceControlQueue()).to(exchange).with(DEVICE_CONTROL_QUEUE);
    }

}
