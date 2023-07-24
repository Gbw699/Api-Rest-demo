FROM openjdk:17-jdk-alpine
COPY target/Api-REST-demo-1.0.0.jar api-rest-demo.jar
ENTRYPOINT ["java", "-jar", "api-rest-demo.jar"]