package org.jimin.sensorapi.repository;

import org.jimin.sensorapi.entity.MqttSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MqttSubscriptionRepository extends JpaRepository<MqttSubscription, Integer> {
    
}
