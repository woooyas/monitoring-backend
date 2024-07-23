package org.jimin.sensorapi.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import lombok.RequiredArgsConstructor;
import org.jimin.sensorapi.config.InfluxdbConfig;
import org.jimin.sensorapi.dto.SensorData;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InfluxdbService {

    private final InfluxdbConfig influxdbConfig;

    public void saveSensorData(SensorData sensorData) {
        InfluxDBClient influxDBClient = InfluxDBClientFactory.create(influxdbConfig.getUrl(),
                                                                     influxdbConfig.getToken().toCharArray(),
                                                                     influxdbConfig.getOrg(),
                                                                     influxdbConfig.getBucket());
        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
        Point point = Point.measurement(sensorData.getMeasurement())
                           .time(sensorData.getTime(), WritePrecision.MS)
                           .addTag("place", sensorData.getPlace())
                           .addTag("deviceId", sensorData.getDeviceId())
                           .addField("topic", sensorData.getTopic())
                           .addField("value", Float.parseFloat(sensorData.getValue()));
        writeApi.writePoint(point);
        influxDBClient.close();
    }
}
