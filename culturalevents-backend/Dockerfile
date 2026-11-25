FROM eclipse-temurin:21-jdk AS build-image
WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN ./mvnw dependency:go-offline

COPY ./src/main/ ./src/main/

RUN ./mvnw clean package


FROM eclipse-temurin:21-jre AS deploy-image

COPY --from=build-image /app/target/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]