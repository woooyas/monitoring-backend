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
public class SensorStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int statusId;

    @ManyToOne
    @JoinColumn(name = "deviceId", referencedColumnName = "deviceId", nullable = false)
    private Sensor sensor;

    @Column(nullable = false)
    private long time;

    @Column(nullable = false)
    private int batteryLevel;
}
