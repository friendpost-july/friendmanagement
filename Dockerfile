FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app-dev
COPY . .
RUN chmod +x ./gradlew && ./gradlew clean build -x test

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app-dev/build/libs/friendmanagement-0.0.1-SNAPSHOT.jar ./friendmanagement-0.0.1.jar

CMD ["java", "-jar", "friendmanagement-0.0.1.jar"]

EXPOSE 19090
