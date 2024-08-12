package org.jimin.sensorapi.service;

import lombok.RequiredArgsConstructor;
import org.jimin.sensorapi.entity.SensorData;
import org.jimin.sensorapi.repository.SensorDataRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SensorDataService {

    private final SensorDataRepository sensorDataRepository;

    public List<SensorData> getTemperaturesForDashboard() {
        String measurement = "temperature";
        List<String> places = Arrays.asList("outdoor", "class_a", "office", "lobby");
        long sixHoursAgo = Instant.now().minusSeconds(6 * 3600).toEpochMilli();

        return sensorDataRepository.findRecentData(measurement, places, sixHoursAgo);
    }

    public Page<SensorData> getRecentDataPageByPlacesAndMeasurement(String measurement, List<String> places, Pageable pageable) {
        return sensorDataRepository.findRecentDataPageByPlacesAndMeasurement(places, measurement, pageable);
    }

    public Page<SensorData> getDataPage(List<String> places, List<String> measurements, Pageable pageable) {
        if (isNotEmpty(measurements) && isNotEmpty(places)) {
            return sensorDataRepository.findDataPageByPlacesAndMeasurements(places, measurements, pageable);
        }
        if (isNotEmpty(places)) {
            return sensorDataRepository.findDataPageByPlaces(places, pageable);
        }
        if (isNotEmpty(measurements)) {
            return sensorDataRepository.findDataPageByMeasurements(measurements, pageable);
        }
        return sensorDataRepository.findDataPage(pageable);
    }

    public List<SensorData> getTodayData(List<String> places, String measurement) {
        long yesterday = new Date().getTime() - 86400000;
        return sensorDataRepository.findTodayData(places, measurement, yesterday);
    }

    private boolean isNotEmpty(List<?> list) {
        return list != null && !list.isEmpty();
    }
}
