
FROM maven:3.3.1-openjdk:22-jdk AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:22-jdk-slim

COPY --from=build /target/ProjectManagementTool-0.0.1-SNAPSHOT.jar render.jsr
EXPOSE  8080
ENTRYPOINT ["java","-jar","render.jar"]