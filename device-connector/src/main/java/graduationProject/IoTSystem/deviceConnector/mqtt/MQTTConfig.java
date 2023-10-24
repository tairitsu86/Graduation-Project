package graduationProject.IoTSystem.deviceConnector.mqtt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import java.time.Instant;

@Configuration
@IntegrationComponentScan(basePackages = "graduationProject.IoTSystem.deviceConnector.mqtt")
@RequiredArgsConstructor
@Getter
public class MQTTConfig {
    @Value("${mqtt.username}")
    private String username;
    @Value("${mqtt.password}")
    private String password;
    @Value("${mqtt.host.ip}")
    private String MQTT_HOST_IP;
    @Value("${mqtt.host.port}")
    private String MQTT_HOST_PORT;

    private final String CLIENT_ID = "Device-Connector";
    public static final String TEST_TOPIC = "test";
    public static final String INFO_TOPIC = "devices/info";
    public static final String STATE_TOPIC = "devices/state";

    private final MQTTMessageListener mqttMessageListener;
    @Bean
    public MqttConnectOptions getMqttConnectOptions(){
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
//        options.setUserName(username);
//        options.setPassword(password.toCharArray());
        options.setServerURIs(new String[]{String.format("tcp://%s:%s", MQTT_HOST_IP, MQTT_HOST_PORT)});
        options.setConnectionTimeout(10);
        options.setMaxInflight(50);
        options.setKeepAliveInterval(20);
        return options;
    }

    @Bean
    public MqttPahoClientFactory getMqttPahoClientFactory(){
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getMqttConnectOptions());
        return factory;
    }

    @Bean(name = "mqttPublishChannel")
    public MessageChannel getMqttPublishChannel() {
        return new DirectChannel();
    }
    @Bean
    @ServiceActivator(inputChannel = "mqttPublishChannel")
    public MessageHandler mqttPublishMessageHandler() {
        MqttPahoMessageHandler messageHandler =  new MqttPahoMessageHandler(CLIENT_ID, getMqttPahoClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(TEST_TOPIC);
        return messageHandler;
    }

    @Bean(name = "mqttReceiveChannel")
    public MessageChannel getMqttReceiveChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttReceiveChannel")
    public MessageHandler mqttReceiveMessageHandler() {
        return mqttMessageListener;
    }

    @Bean
    public MessageProducerSupport mqttListenerSetting(MqttPahoClientFactory mqttClientFactory) {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(CLIENT_ID + "-sub-" + Instant.now().toEpochMilli(), mqttClientFactory,
                        TEST_TOPIC,INFO_TOPIC,STATE_TOPIC);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(2);
        adapter.setOutputChannel(getMqttReceiveChannel());
        return adapter;
    }

}
