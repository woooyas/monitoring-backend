FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
COPY ./target/mock-sensor-api-0.0.1-SNAPSHOT.jar mock-sensor-api.jar
ENTRYPOINT ["java", "-jar", "/mock-sensor-api.jar"]
