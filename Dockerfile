# Этап сборки
FROM eclipse-temurin:22-jdk AS build

# Задаем рабочую директорию
WORKDIR /app

# Копируем все файлы проекта
COPY . .

# Собираем проект с использованием локально установленного Gradle
RUN ./gradlew clean build -x test

# Этап запуска
FROM eclipse-temurin:22-jre

# Задаем рабочую директорию
WORKDIR /app

# Копируем собранный JAR из предыдущего этапа
COPY --from=build /app/build/libs/*.jar app.jar

# Открываем порт
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]
