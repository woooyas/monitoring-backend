package org.jimin.sensorapi.config;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "mqtt")
public class MqttConfig {

    private String broker;
    private String clientId;

    @Bean
    public MqttClient mqttClient() throws MqttException {
        MqttClient client = new MqttClient(getBroker(), getClientId(), new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        client.connect(options);
        return client;
    }
}
