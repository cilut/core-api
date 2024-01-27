FROM openjdk:19-jdk-alpine

WORKDIR /app

COPY build/libs/CoreAPI-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]