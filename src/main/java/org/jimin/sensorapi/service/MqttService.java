package org.jimin.sensorapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.jimin.sensorapi.config.MqttConfig;
import org.jimin.sensorapi.dto.SensorData;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class MqttService {

    private final MqttConfig mqttConfig;
    private final ObjectMapper objectMapper;
    private final InfluxdbService influxdbService;
    private MqttClient mqttClient;


    public void subscribeAndSave(String topicFilter) {
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
                    SensorData data = mapToSensorData(topic, message.toString());
                    if (data == null) {
                        return;
                    }
                    influxdbService.saveSensorData(data);
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

    private SensorData mapToSensorData(String topic, String message) {
        String[] topics = topic.split("/");
        try {
            Map<String, Object> map = objectMapper.readValue(message, Map.class);
            return SensorData.builder()
                             .time(Long.parseLong(map.get("time").toString()))
                             .value(map.get("value").toString())
                             .place(topics[6])
                             .deviceId(topics[8])
                             .measurement(topics[10])
                             .topic(topic)
                             .build();
        } catch (JsonProcessingException e) {
            log.error("mqtt 메세지 처리 실패({}): ", message, e);
            return null;
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
