package org.jimin.sensorapi.controller;

import lombok.RequiredArgsConstructor;
import org.jimin.sensorapi.entity.SensorStatus;
import org.jimin.sensorapi.service.SensorStatusService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sensor-statuses")
public class SensorStatusController {

    private final SensorStatusService sensorStatusService;

    @GetMapping("/latest")
    public Page<SensorStatus> getLatestSensorStatuses(Pageable pageable) {
        return sensorStatusService.getLatestSensorStatuses(pageable);
    }

    @GetMapping("/low-battery")
    public Page<SensorStatus> getBatteryLevelAscStatuses(Pageable pageable) {
        return sensorStatusService.getBatteryLevelAscStatuses(pageable);
    }
}
