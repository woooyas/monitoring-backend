package org.jimin.sensorapi.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SensorData {
    private Long time;
    private String value;
    private String place;
    private String deviceId;
    private String measurement;
    private String topic;
}
