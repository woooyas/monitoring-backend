package org.jimin.sensorapi;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
public class SensorApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SensorApiApplication.class, args);
    }
}
