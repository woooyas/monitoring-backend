package org.jimin.sensorapi.repository;

import org.jimin.sensorapi.entity.SensorStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SensorStatusRepository extends JpaRepository<SensorStatus, Integer> {
    @Query("SELECT ss FROM SensorStatus ss")
    Page<SensorStatus> findLatestSensorStatusByDeviceId(Pageable pageable);

    @Query("SELECT s FROM SensorStatus s WHERE s.time = " +
            "(SELECT MAX(ss.time) FROM SensorStatus ss WHERE ss.sensor.deviceId = s.sensor.deviceId)" +
            "ORDER BY s.batteryLevel ASC")
    Page<SensorStatus> findByBatteryLevelAsc(Pageable pageable);
}
