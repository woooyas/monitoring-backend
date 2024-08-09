package org.jimin.sensorapi.repository;

import org.jimin.sensorapi.entity.Sensor;
import org.jimin.sensorapi.entity.SensorData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SensorDataRepository extends JpaRepository<Sensor, Integer> {

    @Query("SELECT sd FROM SensorData sd WHERE sd.measurement = :measurement " +
            "AND sd.place IN (:places) AND sd.time >= :timeRange ORDER BY sd.time DESC")
    List<SensorData> findRecentData(
            @Param("measurement") String measurement,
            @Param("places") List<String> places,
            @Param("timeRange") long timeRange);


    @Query("SELECT sd FROM SensorData sd WHERE sd.place IN (:places) " +
            "AND sd.measurement = :measurement ORDER BY sd.time DESC")
    Page<SensorData> findRecentDataPage(
            @Param("places") List<String> places,
            @Param("measurement") String measurement,
            Pageable pageable);
}
