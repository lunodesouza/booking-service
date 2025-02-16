# Stage 1: Build
FROM maven:3.9.9-amazoncorretto-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests --no-transfer-progress -T 1C

# Stage 2: Run
FROM amazoncorretto:21-alpine
WORKDIR /app
COPY --from=build /app/target/booking-service.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
