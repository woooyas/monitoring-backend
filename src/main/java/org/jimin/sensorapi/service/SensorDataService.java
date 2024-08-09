package org.jimin.sensorapi.service;

import lombok.RequiredArgsConstructor;
import org.jimin.sensorapi.entity.SensorData;
import org.jimin.sensorapi.repository.SensorDataRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
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

    public Page<SensorData> getRecentDataPage(String measurement, List<String> places, Pageable pageable) {
        //        List<String> places = Arrays.asList("lobby", "class_a", "class_b", "office", "server_room", "storage");

        return sensorDataRepository.findRecentDataPage(places, measurement, pageable);
    }
}
