package org.jimin.sensorapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.jimin.sensorapi.config.MqttConfig;
import org.jimin.sensorapi.dto.SensorValue;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;

@Slf4j
@Service
@RequiredArgsConstructor
public class MqttService {

    private final MqttConfig mqttConfig;
    private final ObjectMapper objectMapper;
    private MqttClient mqttClient;

    public void subscribe(String topicFilter) {
        try {
            mqttClient = mqttConfig.mqttClient();
            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    log.error("mqtt 연결 실패: ", cause);
                    try {
                        mqttClient.reconnect();
                    } catch (MqttException e) {
                        log.error("mqtt 재연결 실패: ", e);
                    }
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    try {
                        SensorValue sensorValue = objectMapper.readValue(message.toString(), SensorValue.class);

                    } catch (JsonProcessingException e) {
                        log.error("mqtt 메세지 처리 실패({}): ", message, e);
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                }
            });
            mqttClient.subscribe(topicFilter, 2);
        } catch (MqttException e) {
            log.error("MqttException: ", e);
        }
    }

    @PreDestroy
    public void destroy() {
        try {
            if (mqttClient != null) {
                mqttClient.disconnect();
                mqttClient.close();
            }
        } catch (MqttException e) {
            log.error("Mqtt 클라이언트 종료 실패: ", e);
        }
    }
}
