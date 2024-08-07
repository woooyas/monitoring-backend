package org.jimin.sensorapi.repository;

import org.jimin.sensorapi.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorDataRepository extends JpaRepository<Sensor, Integer> {
}
