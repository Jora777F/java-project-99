FROM eclipse-temurin:21-jdk AS build

WORKDIR /app
COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew build --no-daemon

# Финальный образ
FROM eclipse-temurin:21-jre

WORKDIR /app

# Копируем JAR из стадии build
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]