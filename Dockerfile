#
#FROM maven:3.9.5-openjdk:22-jdk AS build
#COPY . .
#RUN mvn clean package -DskipTests
#
#FROM openjdk:22-jdk-slim
#
#COPY --from=build /target/ProjectManagementTool-0.0.1-SNAPSHOT.jar render.jsr
#EXPOSE  8080
#ENTRYPOINT ["java","-jar","render.jar"]


# Use Maven with OpenJDK 21
FROM maven:3.9.5-eclipse-temurin-21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the project files
COPY . .

# Build the project and skip tests
RUN mvn clean package -DskipTests

# Use a lightweight OpenJDK image to run the Spring Boot application
FROM eclipse-temurin:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/ProjectManagementTool-0.0.1-SNAPSHOT.jar render.jar

# Expose the port Spring Boot runs on
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "render.jar"]
