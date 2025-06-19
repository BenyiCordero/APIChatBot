FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

COPY . .

WORKDIR /app/chatbotStatistics

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=build /app/chatbotStatistics/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
