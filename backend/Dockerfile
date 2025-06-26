# === STAGE 1: Build JAR with Maven ===
FROM maven:3.9-eclipse-temurin-24 AS build
WORKDIR /app

# Copy source code
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# === STAGE 2: Run the app ===
FROM eclipse-temurin:24-jdk
WORKDIR /app

# Copy the built JAR from stage 1
COPY --from=build /app/target/*.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
