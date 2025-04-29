FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/*.jar app-be.jar
ENTRYPOINT ["java", "-jar", "app-be.jar"]
