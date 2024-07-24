package org.jimin.sensorapi;

import lombok.RequiredArgsConstructor;
import org.jimin.sensorapi.entity.MqttSubscription;
import org.jimin.sensorapi.repository.MqttSubscriptionRepository;
import org.jimin.sensorapi.service.MqttService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@RequiredArgsConstructor
@SpringBootApplication
public class SensorApiApplication implements CommandLineRunner {

    private final MqttService mqttService;
    private final MqttSubscriptionRepository mqttSubscriptionRepository;

    public static void main(String[] args) {
        SpringApplication.run(SensorApiApplication.class, args);
    }

    @Override
    public void run(String... args) {
        List<MqttSubscription> mqttList = mqttSubscriptionRepository.findAll();
        for (MqttSubscription subscription : mqttList) {
            mqttService.subscribeAndSave(subscription.getTopic(), subscription.getQos());
        }
    }
}
