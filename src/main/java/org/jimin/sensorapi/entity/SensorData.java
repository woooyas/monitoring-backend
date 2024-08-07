package org.jimin.sensorapi.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table
public class SensorData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dataId;

    @ManyToOne
    @JoinColumn(name = "deviceId", referencedColumnName = "deviceId", nullable = false)
    private Sensor sensor;

    @Column(nullable = false)
    private String topic;

    @Column(nullable = false)
    private String place;

    @Column(nullable = false)
    private String measurement;

    @Column(nullable = false)
    private long time;

    @Column(nullable = false)
    private float value;
}
