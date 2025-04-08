
FROM eclipse-temurin:22-jdk AS build
WORKDIR /app
COPY . .

RUN ./gradlew clean build -x test

FROM eclipse-temurin:22-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
