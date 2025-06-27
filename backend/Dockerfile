# Use a full JDK base image (includes javac internals)
FROM eclipse-temurin:21-jdk AS builder

# Set working directory
WORKDIR /app

# Copy pom and download dependencies first (for better caching)
COPY pom.xml ./
RUN apt-get update && apt-get install -y maven
RUN mvn dependency:go-offline -B

# Copy source code
COPY . .

# Package the application (compile triggers annotation processing)
RUN mvn clean package -DskipTests

# Second stage: run with smaller JRE (optional)
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/phoenix-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
