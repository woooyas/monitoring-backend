package org.jimin.sensorapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "influxdb")
public class InfluxdbConfig {
    private String url;
    private String token;
    private String org;
    private String bucket;
}
