package org.jimin.sensorapi.controller;

import lombok.RequiredArgsConstructor;
import org.jimin.sensorapi.dto.SensorNameUpdateRequest;
import org.jimin.sensorapi.entity.Sensor;
import org.jimin.sensorapi.service.SensorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sensors")
public class SensorController {

    private final SensorService sensorService;

    @PutMapping("/{sensorId}/name")
    public ResponseEntity<Sensor> updateSensorName(@PathVariable int sensorId, @RequestBody SensorNameUpdateRequest request) {
        Sensor updatedSensor = sensorService.updateSensorName(sensorId, request.getSensorName());
        return ResponseEntity.ok(updatedSensor);
    }
}
