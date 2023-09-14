package graduationProject.IoTSystem.deviceConnector.mqtt;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
@IntegrationComponentScan(basePackages = "graduationProject.IoTSystem.deviceConnector.mqtt")
public class MQTTConfig {
    private String username;
    private String password;
    private String hostUrl = "tcp://127.0.0.1:1883";
    private String clientId = "OAO";
    private String defaultTopic = "test";
    private static final String INFO_TOPIC = "info";
    private static final String STATE_TOPIC = "state";


    @Bean
    public MqttConnectOptions getMqttConnectOptions(){
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
//        options.setUserName(username);
//        options.setPassword(password.toCharArray());
        options.setServerURIs(new String[]{hostUrl});
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
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler =  new MqttPahoMessageHandler(clientId, getMqttPahoClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(defaultTopic);
        return messageHandler;
    }


}
