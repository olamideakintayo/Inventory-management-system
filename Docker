# ============================
# Stage 1 — Build the application
# ============================
FROM maven:3.9.6-eclipse-temurin-21 AS build
LABEL stage=builder

# Set working directory inside container
WORKDIR /app

# Copy Maven files first for dependency caching
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Download all dependencies (so builds are faster later)
RUN mvn dependency:go-offline

# Copy your actual source code
COPY src src

# Build the Spring Boot JAR (skip tests for faster builds)
RUN mvn clean package -DskipTests


# ============================
# Stage 2 — Runtime environment
# ============================
# Use Microsoft OpenJDK 21 (Ubuntu base)
FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu

# Set working directory
WORKDIR /app

# Copy only the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose your app’s port (Spring Boot default = 8080)
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]
