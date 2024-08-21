package org.jimin.sensorapi.service;

import lombok.RequiredArgsConstructor;
import org.jimin.sensorapi.entity.SensorData;
import org.jimin.sensorapi.repository.SensorDataRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SensorDataService {

    private final SensorDataRepository sensorDataRepository;

    public List<SensorData> getTemperaturesForDashboard() {
        String measurement = "temperature";
        List<String> places = Arrays.asList("outdoor", "class_a", "office", "lobby");
        long sixHoursAgo = Instant.now().minusSeconds(6 * 3600).toEpochMilli();

        return sensorDataRepository.findRecentDataByPlacesAndMeasurementAndTime(places, measurement, sixHoursAgo);
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

        return sensorDataRepository.findRecentDataByPlacesAndMeasurementAndTime(places, measurement, yesterday);
    }

    public Map<String, Double> getRecentAndYesterdayAverageValue(List<String> places, String measurement) {
        Pageable recentPageable = PageRequest.of(0, 1);
        Pageable yesterdayPageable = PageRequest.of(0, 1, Sort.by("time").ascending());

        List<Float> recentValues = new ArrayList<>();
        List<Float> yesterdayValues = new ArrayList<>();

        for (String place : places) {
            SensorData sensorData = sensorDataRepository.findRecentDataPageByPlaceAndMeasurement(place, measurement, recentPageable).getContent().get(0);
            recentValues.add(sensorData.getValue());
            yesterdayValues.add(sensorDataRepository.findRecentDataPageByPlaceAndMeasurementAndTime(place, measurement, sensorData.getTime() - 86460000, yesterdayPageable).get(0).getValue());
        }

        return Map.of("recent", getAvg(recentValues), "yesterday", getAvg(yesterdayValues));
    }

    private boolean isNotEmpty(List<?> list) {
        return list != null && !list.isEmpty();
    }

    private double getAvg(List<Float> list) {
        float sum = 0;
        for (Float f : list) {
            sum += f;
        }

        return Math.round(sum / list.size() * 10.0) / 10.0;
    }
}
