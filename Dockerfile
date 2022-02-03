FROM openjdk:11
ARG JAR_FILE=/build/libs/patient-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} patient-service.jar
CMD ["java", "-jar", "-Dspring.profiles.active=local", "/patient-service.jar"]
