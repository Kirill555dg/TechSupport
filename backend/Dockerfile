# Используем официальный образ OpenJDK с 21 версией Java
FROM maven:3.9.4-eclipse-temurin-21 AS build
# Указываем рабочую директорию
WORKDIR /app
# Копируем файл проекта Maven и зависимости
COPY pom.xml ./
RUN mvn -B dependency:go-offline
# Копируем весь проект и собираем приложение
COPY . .
RUN mvn -B package -DskipTests

# Создаём минимальный runtime-образ
FROM eclipse-temurin:21-jdk
# Указываем рабочую директорию для runtime-образа
WORKDIR /app
# Копируем собранный jar-файл из предыдущего этапа
COPY --from=build /app/target/*.jar app.jar
# Указываем команду запуска Spring Boot приложения
CMD ["java", "-jar", "app.jar"]