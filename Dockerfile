FROM maven:3.9-eclipse-temurin-17-alpine AS builder

WORKDIR /api

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package

FROM eclipse-temurin:17-jre-alpine

WORKDIR /api

COPY --from=builder /api/target/*.jar mycontacts.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "mycontacts.jar"]