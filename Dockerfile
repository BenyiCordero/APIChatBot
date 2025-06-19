FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Instalamos Maven
RUN apt-get update && apt-get install -y maven

COPY . .

WORKDIR /app/chatBotStadistics


RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/chatBotStadistics/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]

