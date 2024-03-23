FROM openjdk:17-jdk-alpine
COPY target/security.jar security.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "security.jar"]