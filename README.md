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

# Endpoints abiertos

## Auth (Autenticación de los Admin Users)
- `POST /auth/register`  
  Inserta un nuevo Admin User.  
  **JSON de solicitud:**
  ```json
  {
    "name": "usuario",
    "email": "usuario@gmail.com",
    "password": "1234"
  }
  ```

- `POST /auth/login`  
  Autentica al usuario y devuelve un Bearer token.  
  **JSON de solicitud:**
  ```json
  {
    "email": "usuario@gmail.com",
    "password": "1234"
  }
  ```

## Consultas (Administración de las consultas)
- `GET /por-temav2`  
  Obtiene las estadísticas agrupadas por tema.  
  **Parámetros opcionales:** `year`, `month`, `week`  
  **JSON de respuesta:**
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
  **JSON de respuesta:**
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
  **JSON de respuesta:**
  ```json
  1523
  ```

- `GET /cantidad-usuarios`  
  Devuelve la cantidad total de usuarios únicos.  
  **Parámetros opcionales:** `year`, `month`, `week`  
  **JSON de respuesta:**
  ```json
  287
  ```

- `GET /api/stats`  
  Obtiene estadísticas agregadas generales (conteos y métricas resumidas).  
  **JSON de respuesta:**
  ```json
  {
    "totalConsultas": 1523,
    "consultasHoy": 34,
    "topTemas": {
      "Programación": 45.8,
      "Matemáticas": 32.5
    }
  }
  ```

- `GET /api/stats/details`  
  Obtiene registros paginados de interacciones (consulta, respuesta, usuario, timestamp).  
  **Parámetros opcionales:** `page`, `size`  
  **JSON de respuesta (ejemplo de un item):**
  ```json
  {
    "id": 123,
    "userId": "usuario123",
    "topic": "bases de datos",
    "subtopic": "consultas",
    "question": "¿Cómo se hace un JOIN?",
    "answer": "Explicación...",
    "timestamp": "2025-10-07T14:00:00Z",
    "metadata": { "source": "whatsapp", "sessionId": "abc123" }
  }
  ```

- `GET /api/stats/filter`  
  Filtrado avanzado de estadísticas por query params (fechas, tema, subtema, usuario).  
  **Parámetros opcionales:** `startDate`, `endDate`, `topic`, `subtopic`, `userId`, `page`, `size`  
  **JSON de respuesta:** similar a `/api/stats` o `/api/stats/details` según nivel de agregación.

- `POST /prompt/create`  
  Crea un nuevo prompt del sistema.  
  **JSON de solicitud:**
  ```json
  {
    "titulo": "Prompt educativo",
    "descripcion": "Eres un asistente educativo, amable y claro."
  }
  ```
  **JSON de respuesta:**
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
  **JSON de respuesta:**
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
  **JSON de solicitud:**
  ```json
  {
    "titulo": "Prompt técnico actualizado",
    "descripcion": "Eres un asistente enfocado en temas técnicos y precisos."
  }
  ```
  **JSON de respuesta:**
  ```json
  {
    "id": 1,
    "titulo": "Prompt técnico actualizado",
    "descripcion": "Eres un asistente enfocado en temas técnicos y precisos.",
    "fechaActualizacion": "2025-10-07T15:00:00Z"
  }
  ```

## Interacciones (Registro y manejo de consultas individuales)
- `POST /api/interactions`  
  Registra una nueva interacción (consulta + respuesta).  
  **JSON de solicitud:**
  ```json
  {
    "userId": "usuario123",
    "topic": "bases de datos",
    "subtopic": "consultas",
    "question": "¿Cómo se hace un JOIN?",
    "answer": "Explicación...",
    "timestamp": "2025-10-07T14:00:00Z",
    "metadata": { "source": "whatsapp", "sessionId": "abc123" }
  }
  ```
  **JSON de respuesta:** objeto creado con `id` y campos anteriores.

- `GET /api/interactions/{id}`  
  Obtiene una interacción por su id.  
  **JSON de respuesta:** igual al item en `/api/stats/details`.

- `DELETE /api/interactions/{id}`  
  Elimina una interacción por id.  
  **JSON de respuesta:** status o mensaje de confirmación.

## Administración y utilidades
- `GET /api/prompt/current`  
  Devuelve el prompt actualmente en uso por el chatbot.  
  **JSON de respuesta:**
  ```json
  {
    "id": 3,
    "titulo": "Prompt actual",
    "descripcion": "Eres directo y técnico."
  }
  ```

- `PATCH /api/prompt/activate/{id}`  
  Activa un prompt existente para que sea el prompt en uso.  
  **JSON de respuesta:**
  ```json
  {
    "id": 3,
    "activo": true,
    "fechaActivacion": "2025-10-07T16:00:00Z"
  }
  ```

- `GET /health`  
  Estado de salud del servicio.  
  **JSON de respuesta:**
  ```json
  { "status": "UP" }
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
