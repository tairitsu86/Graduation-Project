package graduationProject.IoTSystem.deviceDatabase.rabbitMQ;

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
    public static final String DEVICE_INFO_QUEUE = "IoT-System.Device-Info";
    public static final String DEVICE_CONTROL_QUEUE = "IoT-System.Device-Control";
    public static final String DEVICE_STATE_QUEUE = "IoT-System.Device-State";
    public static final String USER_CONTROL_QUEUE = "IoT-System.User-Control";
    public static final String IoT_DBC_EXCHANGE = "IoT-System_Device-base-communication_Exchange";
    public static final String IoT_UBC_EXCHANGE = "IoT-System_User-base-communication_Exchange";
    public static final String SYS_SVC_EXCHANGE = "System_Service_Exchange";
    public static final String SYS_NTF_EXCHANGE = "System_Notification_Exchange";
    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean("IoT_DBC")
    public TopicExchange exchangeIoT_DBC() {
        return new TopicExchange(IoT_DBC_EXCHANGE);
    }
    @Bean
    public Queue deviceInfoQueue() {
        return new Queue(DEVICE_INFO_QUEUE,false);
    }
    @Bean
    public Queue deviceControlQueue() {
        return new Queue(DEVICE_CONTROL_QUEUE,false);
    }
    @Bean
    public Queue deviceStateQueue() {
        return new Queue(DEVICE_STATE_QUEUE,false);
    }
    @Bean
    public Binding bindingDeviceInfoQueue(@Qualifier("IoT_DBC") TopicExchange exchange) {
        return BindingBuilder.bind(deviceInfoQueue()).to(exchange).with(DEVICE_INFO_QUEUE);
    }
    @Bean
    public Binding bindingDeviceControlQueue(@Qualifier("IoT_DBC") TopicExchange exchange) {
        return BindingBuilder.bind(deviceControlQueue()).to(exchange).with(DEVICE_CONTROL_QUEUE);
    }
    @Bean
    public Binding bindingStateControlQueue(@Qualifier("IoT_DBC") TopicExchange exchange) {
        return BindingBuilder.bind(deviceStateQueue()).to(exchange).with(DEVICE_STATE_QUEUE);
    }



    @Bean("IoT_UBC")
    public TopicExchange exchangeIoT_UBC() {
        return new TopicExchange(IoT_UBC_EXCHANGE);
    }
    @Bean
    public Queue userControlQueue() {
        return new Queue(USER_CONTROL_QUEUE,false);
    }
    @Bean
    public Binding bindingUserControlQueue(@Qualifier("IoT_UBC") TopicExchange exchange) {
        return BindingBuilder.bind(userControlQueue()).to(exchange).with(USER_CONTROL_QUEUE);
    }


    @Bean("SYS_SVC")
    public TopicExchange exchangeSYS_SVC() {
        return new TopicExchange(SYS_SVC_EXCHANGE);
    }
    @Bean
    public Binding bindingUserControlService(@Qualifier("SYS_SVC") TopicExchange exchange) {
        return BindingBuilder.bind(userControlQueue()).to(exchange).with(USER_CONTROL_QUEUE);
    }

    @Bean("SYS_NTF")
    public FanoutExchange exchangeSYS_NTF() {
        return new FanoutExchange(SYS_NTF_EXCHANGE);
    }
}
