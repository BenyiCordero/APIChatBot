# Usa una imagen base de OpenJDK (Java Development Kit)
# openjdk:17-jdk-slim es una buena opción: es ligera y tiene Java 17
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
# Aquí es donde se copiarán los archivos de tu proyecto y donde se ejecutará la aplicación
WORKDIR /app

# Copia los archivos del wrapper de Maven y el pom.xml.
# Esto es para permitir que Docker use el caché de capas de manera eficiente.
# Si solo cambias el código fuente (no las dependencias), Docker no necesita descargar las dependencias de nuevo.
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Ejecuta la fase de resolución de dependencias de Maven.
# Esto asegura que todas las dependencias se descarguen.
RUN ./mvnw dependency:resolve

# Copia el código fuente completo de tu aplicación al directorio de trabajo en el contenedor
COPY src ./src

# Construye la aplicación Spring Boot.
# La fase 'package' de Maven crea el JAR ejecutable en el directorio 'target'.
# -Dmaven.test.skip=true omite la ejecución de los tests durante la compilación, acelerando el build.
RUN ./mvnw package -Dmaven.test.skip=true

# Expone el puerto en el que tu aplicación Spring Boot escucha.
# Render (y Docker en general) mapeará el tráfico externo al puerto especificado aquí.
# Asegúrate de que este puerto coincida con 'server.port' en tu application.properties,
# aunque Render lo sobreescribirá con su variable de entorno $PORT.
EXPOSE 8081

# Define el comando que se ejecutará cuando el contenedor se inicie.
# Esto ejecuta el JAR ejecutable de tu aplicación Spring Boot.
# Necesitas reemplazar 'your-app-name-0.0.1-SNAPSHOT.jar' con el nombre exacto de tu archivo JAR generado.
# El JAR suele estar en la carpeta 'target/' y su nombre incluye la versión de tu proyecto.
ENTRYPOINT ["java", "-jar", "target/chatBotStadistics-0.0.1-SNAPSHOT.jar"]
