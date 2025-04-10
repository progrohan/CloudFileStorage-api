Облачное хранилище файлов
Описание

Многопользовательское файловое облако. Пользователи сервиса могут использовать его для загрузки и хранения файлов. Проект написан в стиле REST API.

Использованные технологии / инструменты
Backend

    Spring Boot
    Spring Security
    Spring Sessions
    Lombok
    Mapstruct
    Gradle
    Swagger

Database

    Spring Data JPA
    PostgreSQL
    Redis
    Minio
    Liquibase

Testing

    JUnit 5
    Testcontainers

Deploy

    Docker

Зависимости

    Java 17+
    Docker

Установка проекта

    Склонируйте репозиторий

https://github.com/progrohan/CloudFileStorage-api

    
    Создайте в корне проекта .env файл и заполните по следующему шаблону:

````
DB_HOST= 
DB_PORT=
DB_NAME=
DB_USERNAME=
DB_PASSWORD=

MINIO_URL=
MINIO_ACCESS_KEY=
MINIO_SECRET_KEY=
ROOT_BUCKET=user-file
````
    Откройте  консоль и пропишите docker compose up -d
    
