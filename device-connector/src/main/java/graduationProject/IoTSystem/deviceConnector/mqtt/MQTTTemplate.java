package graduationProject.IoTSystem.deviceConnector.mqtt;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@MessagingGateway(defaultRequestChannel = "mqttPublishChannel")
public interface MQTTTemplate {
    void publish(String payload);

    void publish(String payload, @Header(MqttHeaders.TOPIC) String topic);

    void publish(String payload, @Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos);
}
