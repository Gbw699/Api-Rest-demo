FROM openjdk:17-jdk-alpine
COPY target/Servlet-demo-full-1.0.0.jar servlet-app.jar
ENTRYPOINT ["java", "-jar", "servlet-app.jar"]