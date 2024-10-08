package org.jimin.sensorapi.controller;

import lombok.RequiredArgsConstructor;
import org.jimin.sensorapi.entity.SensorData;
import org.jimin.sensorapi.service.SensorDataService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sensor-data")
public class SensorDataController {

    private final SensorDataService sensorDataService;

    @GetMapping("/recent-temperature")
    public ResponseEntity<List<SensorData>> getRecentTemperatureData() {
        List<SensorData> sensorData = sensorDataService.getTemperaturesForDashboard();
        return ResponseEntity.ok(sensorData);
    }

    @GetMapping("/recent-{measurement}")
    public ResponseEntity<Page<SensorData>> getRecentDataPageByPlace(@PathVariable String measurement, @RequestParam List<String> places, Pageable pageable) {
        Page<SensorData> sensorDataList = sensorDataService.getRecentDataPageByPlacesAndMeasurement(measurement, places, pageable);
        return ResponseEntity.ok(sensorDataList);
    }

    @GetMapping("/recent-data")
    public ResponseEntity<Page<SensorData>> getDataPageByPlaceAndMeasurement(@RequestParam(required = false) List<String> places, @RequestParam(required = false) List<String> measurements, Pageable pageable) {
        Page<SensorData> sensorDataList = sensorDataService.getDataPage(places, measurements, pageable);
        return ResponseEntity.ok(sensorDataList);
    }

    @GetMapping("/today-data")
    public ResponseEntity<List<SensorData>> getTodayData(@RequestParam(required = false) List<String> places, @RequestParam(required = false) String measurement) {
        return ResponseEntity.ok(sensorDataService.getTodayData(places, measurement));
    }

    @GetMapping("/recent-and-yesterday")
    public ResponseEntity<Map<String, Double>> getRecentAndYesterdayValueMap(@RequestParam(required = false) List<String> places, @RequestParam(required = false) String measurement) {
        return ResponseEntity.ok(sensorDataService.getRecentAndYesterdayAverageValue(places, measurement));
    }

    @GetMapping("/range")
    public ResponseEntity<List<SensorData>> findDataByMeasurementAndTimeRange(@RequestParam String measurement, @RequestParam long startTime, @RequestParam long endTime) {
        return ResponseEntity.ok(sensorDataService.findDataByMeasurementAndTimeRange(measurement, startTime, endTime));
    }

}
