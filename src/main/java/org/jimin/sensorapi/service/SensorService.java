package org.jimin.sensorapi.service;

import lombok.RequiredArgsConstructor;
import org.jimin.sensorapi.entity.Sensor;
import org.jimin.sensorapi.repository.SensorRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class SensorService {

    private final SensorRepository sensorRepository;

    public Sensor updateSensorName(int sensorId, String sensorName) {
        Sensor sensor = sensorRepository.findById(sensorId)
                                        .orElseThrow(() -> new EntityNotFoundException("Sensor not found with id " + sensorId));
        sensor.setSensorName(sensorName);
        return sensorRepository.save(sensor);
    }

}
