# API ChatBot — Estadísticas y administración de prompt

**Repositorio:** `BenyiCordero/APIChatBot` (Java + Dockerfile).

---

## Descripción
API ChatBot es un microservicio para recolectar, consultar y gestionar estadísticas de un chatbot. Permite:  
- Consultar estadísticas agregadas y detalladas.  
- Filtrar las estadísticas por distintos criterios (fecha, tema, subtema, etc.).  
- Actualizar dinámicamente el *system prompt* usado por la aplicación del chatbot para cambiar su comportamiento sin necesidad de redeploy.

---

## Características principales
- Endpoints para estadísticas agregadas (conteos, frecuencias, temas más frecuentes).  
- Filtrado flexible vía query params (rango de fechas, temas, subtemas, etc.).  
- Endpoint para obtener y actualizar el prompt del sistema (soporta GET y POST).  
- Contenerización mediante `Dockerfile` para despliegue sencillo.

---

## Requisitos
- Java 17 
- Maven
- MySQL (configurable vía variables de entorno).  
- Docker (opcional, para ejecutar la imagen construida con el `Dockerfile`).  
- Variables de entorno para configuración (ver sección `Configuración`).

---

## Estructura (general)
```
/chatBotStadistics      # Código principal del microservicio (controladores, servicios, repositorios, modelos)
Dockerfile              # Para construir la imagen del servicio
.gitignore
.idea/                   # Configs de IDE (opcionales)
ngrok.exe                # (herramienta incluida para tunelizar, opcional)
```

---

## Configuración (variables de entorno sugeridas)
Ajusta estas variables según tu entorno (archivo `.env` o variables del sistema):

```
APP_PORT=8080
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/chatbotdb
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=secret
SPRING_JPA_HIBERNATE_DDL_AUTO=update
JWT_SECRET=tu_clave_jwt
```
---

## Endpoints abiertos

### Auth (Autenticación de los Admin Users)
- `POST /auth/register`  
  Hace la isnerción de un nuevo Admin User, se utiliza el siguiente JSON:
  `{
  "name": "usuario",
  "email" : "usuario@gmail.com",
  "password": "1234"
  }
  `

- `POST /auth/login`  
  El usuario se autentifica y recibe un Bearer token, se utiliza el siguiente JSON:
  `{
  "email" : "usuario@gmail.com",
  "password": "1234"
  }
  `

### Consultas (Administración de las consultas)

- `GET /consultas/por-tema/`  
  Filtrado avanzado por query params. Ejemplos de params:
  - `startDate=YYYY-MM-DD`
  - `endDate=YYYY-MM-DD`
  - `topic=matematicas`
  - `subtopic=algebra`
  - `userId=123`
  - `page=0`
  - `size=50`
# Endpoints adicionales del API ChatBot

- `GET /por-temav2`  
  Obtiene las estadísticas agrupadas por tema.  
  **Parámetros opcionales:** `year`, `month`, `week`  
  **Ejemplo JSON de respuesta:**
  ```json
  {
    "Matemáticas": 32.5,
    "Inglés": 21.7,
    "Programación": 45.8
  }
  ```

- `GET /por-subtemav2`  
  Devuelve las estadísticas agrupadas por subtema.  
  **Parámetros opcionales:** `year`, `month`, `week`  
  **Ejemplo JSON de respuesta:**
  ```json
  {
    "Álgebra": 18.4,
    "Gramática": 22.9,
    "Spring Boot": 58.7
  }
  ```

- `GET /`  
  Retorna el total de consultas realizadas al chatbot.  
  **Parámetros opcionales:** `year`, `month`, `week`  
  **Ejemplo JSON de respuesta:**
  ```json
  1523
  ```

- `GET /cantidad-usuarios`  
  Devuelve la cantidad total de usuarios únicos.  
  **Parámetros opcionales:** `year`, `month`, `week`  
  **Ejemplo JSON de respuesta:**
  ```json
  287
  ```

- `POST /prompt/create`  
  Crea un nuevo prompt del sistema.  
  **Ejemplo JSON de solicitud:**
  ```json
  {
    "titulo": "Prompt educativo",
    "descripcion": "Eres un asistente educativo, amable y claro."
  }
  ```
  **Ejemplo JSON de respuesta:**
  ```json
  {
    "id": 1,
    "titulo": "Prompt educativo",
    "descripcion": "Eres un asistente educativo, amable y claro.",
    "fechaCreacion": "2025-10-07T14:00:00Z"
  }
  ```

- `GET /prompt/{id}`  
  Obtiene un prompt específico por su identificador.  
  **Ejemplo JSON de respuesta:**
  ```json
  {
    "id": 1,
    "titulo": "Prompt educativo",
    "descripcion": "Eres un asistente educativo, amable y claro.",
    "fechaCreacion": "2025-10-07T14:00:00Z"
  }
  ```

- `PUT /prompt/actualizar/{id}`  
  Actualiza un prompt existente.  
  **Ejemplo JSON de solicitud:**
  ```json
  {
    "titulo": "Prompt técnico actualizado",
    "descripcion": "Eres un asistente enfocado en temas técnicos y precisos."
  }
  ```
  **Ejemplo JSON de respuesta:**
  ```json
  {
    "id": 1,
    "titulo": "Prompt técnico actualizado",
    "descripcion": "Eres un asistente enfocado en temas técnicos y precisos.",
    "fechaActualizacion": "2025-10-07T15:00:00Z"
  }
  ```

## Construcción y ejecución

### Con Maven
```bash
mvn clean package
java -jar target/<nombre-del-jar>.jar
```

### Con Docker
Construir imagen:
```bash
docker build -t apichatbot:latest .
```
Correr contenedor:
```bash
docker run -d --name apichatbot   -p 8080:8080   --env-file .env   apichatbot:latest
```

---

## Contacto
  Benyi Uriel Cordero Sánchez | benyi.uriel2006@gmail.com | benyiiuri@icloud.com
---
