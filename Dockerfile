FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
COPY ./target/sensor-api-0.0.1-SNAPSHOT.jar sensor-api.jar
ENTRYPOINT ["java", "-jar", "/sensor-api.jar"]
