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

    // 시간 범위 설정, 최근순, places, measurement
    @Query("SELECT sd FROM SensorData sd WHERE sd.place IN (:places) AND sd.measurement = :measurement AND sd.time >= :timeRange ORDER BY sd.time DESC")
    List<SensorData> findRecentDataByPlacesAndMeasurementAndTime(@Param("places") List<String> places, @Param("measurement") String measurement, @Param("timeRange") long timeRange);

    // 시간 범위 설정, 최근순, places, measurement
    @Query("SELECT sd FROM SensorData sd WHERE sd.place = :place AND sd.measurement = :measurement AND sd.time >= :timeRange")
    List<SensorData> findRecentDataPageByPlaceAndMeasurementAndTime(@Param("place") String place, @Param("measurement") String measurement, @Param("timeRange") long timeRange, Pageable pageable);

    // 최근순, 페이지, places, measurement
    @Query("SELECT sd FROM SensorData sd WHERE sd.place IN (:places) AND sd.measurement = :measurement ORDER BY sd.time DESC")
    Page<SensorData> findRecentDataPageByPlacesAndMeasurement(@Param("places") List<String> places, @Param("measurement") String measurement, Pageable pageable);

    // 최근순, 페이지, place, measurement
    @Query("SELECT sd FROM SensorData sd WHERE sd.place = :place AND sd.measurement = :measurement ORDER BY sd.time DESC")
    Page<SensorData> findRecentDataPageByPlaceAndMeasurement(@Param("place") String place, @Param("measurement") String measurement, Pageable pageable);

    // 페이지, places, measurements
    @Query("SELECT sd FROM SensorData sd WHERE sd.place IN (:places) AND sd.measurement IN (:measurements)")
    Page<SensorData> findDataPageByPlacesAndMeasurements(@Param("places") List<String> places, @Param("measurements") List<String> measurements, Pageable pageable);

    // 페이지, measurement
    @Query("SELECT sd FROM SensorData sd WHERE sd.measurement IN (:measurements)")
    Page<SensorData> findDataPageByMeasurements(@Param("measurements") List<String> measurement, Pageable pageable);

    // 페이지, places
    @Query("SELECT sd FROM SensorData sd WHERE sd.place IN (:places)")
    Page<SensorData> findDataPageByPlaces(@Param("places") List<String> places, Pageable pageable);

    // 페이지
    @Query("SELECT sd FROM SensorData sd")
    Page<SensorData> findDataPage(Pageable pageable);
}
