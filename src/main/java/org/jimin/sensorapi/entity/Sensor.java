package org.jimin.sensorapi.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "Sensors")
public class Sensor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sensorId;

    @Setter
    @Column(nullable = false)
    private String sensorName;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String deviceId;
}
