
FROM gradle:8.3-jdk17 AS build

WORKDIR /app

COPY build.gradle settings.gradle /app/
COPY src /app/src

RUN gradle clean bootJar

FROM openjdk:17-jdk-slim AS runtime

WORKDIR /app

COPY --from=build /app/build/libs/mychat-message-container-service.jar mychat-message-container-service.jar

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "mychat-massage-container-service.jar"]
