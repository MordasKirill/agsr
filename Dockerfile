FROM gradle:8.0.2-jdk19 AS build

WORKDIR /home/gradle/src

COPY build.gradle settings.gradle gradlew ./

COPY gradle ./gradle

COPY src ./src

RUN ./gradlew build -x test

FROM openjdk:19-alpine

WORKDIR /app

COPY --from=build /home/gradle/src/build/libs/task-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "./app.jar"]