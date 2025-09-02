# Builder stage: Use Alpine JDK and install Maven
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
# Install Maven
RUN apk add --no-cache maven
COPY pom.xml ./
RUN --mount=type=cache,target=/root/.m2 mvn -T 1C dependency:go-offline -B
COPY . .
RUN mvn -T 1C clean package -DskipTests

# Runtime stage: Use Alpine JRE
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/phoenix-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
