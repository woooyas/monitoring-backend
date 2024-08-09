package org.jimin.sensorapi.controller;

import lombok.RequiredArgsConstructor;
import org.jimin.sensorapi.entity.SensorData;
import org.jimin.sensorapi.service.SensorDataService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Page<SensorData>> getRecentDataPage(@PathVariable String measurement, @RequestParam List<String> places, Pageable pageable) {
        Page<SensorData> sensorDataList = sensorDataService.getRecentDataPage(measurement, places, pageable);
        return ResponseEntity.ok(sensorDataList);
    }
}
