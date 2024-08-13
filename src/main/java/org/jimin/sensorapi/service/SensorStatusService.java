package org.jimin.sensorapi.service;

import lombok.RequiredArgsConstructor;
import org.jimin.sensorapi.entity.SensorStatus;
import org.jimin.sensorapi.repository.SensorStatusRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SensorStatusService {

    private final SensorStatusRepository sensorStatusRepository;

    public Page<SensorStatus> getLatestSensorStatuses(Pageable pageable) {
        return sensorStatusRepository.findLatestSensorStatusByDeviceId(pageable);
    }

    public Page<SensorStatus> getBatteryLevelAscStatuses(Pageable pageable) {
        return sensorStatusRepository.findByBatteryLevelAsc(pageable);
    }
}
