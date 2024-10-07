#FROM ubuntu:latest
#LABEL authors="suhil"
#
#ENTRYPOINT ["top", "-b"]
FROM maven:3.9.5-openjdk-22 AS build
COPY . .
#build the project
RUN mvn clean package -DskipTests

FROM openjdk:22-jdk-slim

COPY --from=build /target/ProjectManagementTool-0.0.1-SNAPSHOT.jar render.jsr
EXPOSE  8080
ENTRYPOINT ["java","-jar","render.jar"]