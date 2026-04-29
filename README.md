# ConflictTracker API - Backend Spring Boot

Backend de l'aplicació **Global Conflict Monitor**, desenvolupat amb **Spring Boot**, **Spring Data JPA**, **PostgreSQL/Supabase** i desplegat a **Railway**.

Aquest backend exposa una API REST per consultar i gestionar conflictes, països, faccions i esdeveniments.

---

## URL pública

Backend desplegat a Railway:

```text
https://conflicttracker-production-b460.up.railway.app
```

Endpoint principal:

```text
https://conflicttracker-production-b460.up.railway.app/api/v1/conflicts
```

Frontend desplegat a Vercel:

```text
https://conflict-monitor-nu.vercel.app
```

API consumida des del frontend mitjançant proxy de Vercel:

```text
https://conflict-monitor-nu.vercel.app/api/v1/conflicts
```

---

## Arquitectura utilitzada

```text
Usuari / Navegador
        |
        v
Frontend Vue 3 + Vite + Pinia
Desplegat a Vercel
https://conflict-monitor-nu.vercel.app
        |
        | Peticions a /api/v1/...
        v
Vercel rewrites / proxy
        |
        v
Backend Spring Boot
Desplegat a Railway
https://conflicttracker-production-b460.up.railway.app
        |
        v
Base de dades PostgreSQL
Supabase
```

El backend està desplegat a Railway i connecta amb una base de dades PostgreSQL externa allotjada a Supabase.

El frontend està desplegat a Vercel. Per evitar problemes de CORS durant el desplegament, Vercel fa de proxy de les peticions `/api/v1/...` cap al backend de Railway.

---

## Tecnologies utilitzades

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- PostgreSQL
- Supabase
- Railway
- Docker
- Maven

---

## Configuració de variables d'entorn

L'aplicació utilitza variables d'entorn per configurar la connexió a la base de dades i el port del servidor.

Configuració principal a `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: ${SPRING_DATASOURCE_URL:jdbc:h2:mem:conflict-tracker}
    driver-class-name: ${SPRING_DATASOURCE_DRIVER_CLASS_NAME:org.h2.Driver}
    username: ${SPRING_DATASOURCE_USERNAME:sa}
    password: ${SPRING_DATASOURCE_PASSWORD:}

  jpa:
    hibernate:
      ddl-auto: ${SPRING_JPA_HIBERNATE_DDL_AUTO:update}
    show-sql: ${SPRING_JPA_SHOW_SQL:true}
    defer-datasource-initialization: true
    properties:
      hibernate:
        dialect: ${SPRING_JPA_DATABASE_PLATFORM:org.hibernate.dialect.PostgreSQLDialect}

  sql:
    init:
      mode: ${SPRING_SQL_INIT_MODE:embedded}

server:
  port: ${PORT:8080}
```

### Variables necessàries a Railway

Per desplegar en un nou entorn de Railway cal configurar:

```env
SPRING_DATASOURCE_URL=jdbc:postgresql://aws-0-eu-west-1.pooler.supabase.com:5432/postgres
SPRING_DATASOURCE_USERNAME=postgres.vqiscjluppvsxfeyipvq
SPRING_DATASOURCE_PASSWORD=YOUR_PASSWORD
SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.postgresql.Driver
SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.PostgreSQLDialect
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_SQL_INIT_MODE=never
FRONTEND_URL=https://conflict-monitor-nu.vercel.app
```

### Desenvolupament local

Si no es configuren variables d'entorn, l'aplicació utilitza H2 en memòria:

```yaml
jdbc:h2:mem:conflict-tracker
```

Això permet executar el backend localment sense necessitat de Supabase.

---

## Dockerfile

S'ha afegit un `Dockerfile` per controlar el procés de build i execució a Railway:

```dockerfile
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD sh -c "java -jar target/*.jar"
```

Aquest fitxer permet a Railway construir la imatge Docker, compilar el projecte Maven i executar el `.jar` generat.

---

## CORS

S'ha afegit una classe de configuració CORS perquè el backend només permeti peticions des del domini del frontend i no utilitzi `*` en producció.

Fitxer:

```text
src/main/java/com/example/ConflictTracker/config/CorsConfig.java
```

Contingut principal:

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${FRONTEND_URL:http://localhost:5173}")
    private String frontendUrl;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(frontendUrl)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false);
    }
}
```

En producció, la variable ha de ser:

```env
FRONTEND_URL=https://conflict-monitor-nu.vercel.app
```

Durant el desplegament actual, Railway va limitar temporalment nous deploys del trial, per això el frontend utilitza també un proxy de Vercel. Tot i així, el backend ja conté la configuració CORS correcta al repositori.

---

## Modificacions realitzades al backend

### 1. Connexió externa amb Supabase

#### Problema inicial

El backend utilitzava H2 en memòria:

```yaml
url: jdbc:h2:mem:conflict-tracker
```

Això feia que les dades no fossin persistents i es perdessin quan l'aplicació es reiniciava.

#### Solució

Es va modificar `application.yaml` per utilitzar variables d'entorn:

```yaml
url: ${SPRING_DATASOURCE_URL:jdbc:h2:mem:conflict-tracker}
username: ${SPRING_DATASOURCE_USERNAME:sa}
password: ${SPRING_DATASOURCE_PASSWORD:}
```

D'aquesta manera:

- En local, si no hi ha variables, usa H2.
- A Railway, usa PostgreSQL/Supabase.

---

### 2. Dockerfile per Railway

#### Problema inicial

Railway no detectava correctament com construir i executar l'aplicació Spring Boot.

#### Error observat

El deploy fallava abans d'arrencar correctament l'aplicació.

#### Solució

Es va afegir un `Dockerfile` amb Java 21 i Maven Wrapper per fer el build de manera controlada.

---

### 3. Port dinàmic per Railway

#### Problema inicial

Spring Boot arrencava al port `8080`, però Railway necessita que l'aplicació respecti la variable d'entorn `PORT`.

#### Solució

Es va afegir:

```yaml
server:
  port: ${PORT:8080}
```

Això permet:

- Local: `8080`
- Railway: el port que indiqui la plataforma

---

### 4. Correcció de `@Lob`

#### Problema inicial

Les entitats tenien camps `description` amb `@Lob`:

```java
@Lob
private String description;
```

Això provocava errors amb Hibernate/H2/PostgreSQL perquè es generaven columnes de tipus `OID`.

#### Error observat

```text
Unknown data type: "OID"
```

i després:

```text
Table "CONFLICT" not found
```

#### Solució

Es va substituir `@Lob` per:

```java
@Column(columnDefinition = "TEXT")
private String description;
```

Aquesta modificació es va aplicar a:

```text
src/main/java/com/example/ConflictTracker/model/Conflict.java
src/main/java/com/example/ConflictTracker/model/Event.java
```

Així el camp `description` és compatible amb PostgreSQL i també evita l'error de tipus `OID`.

---

### 5. Inicialització de dades

#### Problema inicial

`data.sql` podia executar-se en moments no adequats o contra una base de dades on les taules encara no existien.

#### Error observat

```text
Failed to execute SQL script statement
Table "CONFLICT" not found
```

#### Solució

A producció es va configurar:

```env
SPRING_SQL_INIT_MODE=never
```

Això evita que `data.sql` s'executi automàticament en Supabase. Les dades inicials es poden inserir manualment a Supabase o activant temporalment:

```env
SPRING_SQL_INIT_MODE=always
SPRING_JPA_HIBERNATE_DDL_AUTO=create
```

Després de carregar les dades, s'ha de tornar a:

```env
SPRING_SQL_INIT_MODE=never
SPRING_JPA_HIBERNATE_DDL_AUTO=update
```

per evitar sobreescriure dades.

---

## Endpoints principals

### Conflicts

| Mètode | Endpoint | Descripció |
|---|---|---|
| GET | `/api/v1/conflicts` | Llista tots els conflictes |
| GET | `/api/v1/conflicts/{id}` | Obté un conflicte concret |
| GET | `/api/v1/conflicts?status=ACTIVE` | Filtra conflictes per estat |
| POST | `/api/v1/conflicts` | Crea un conflicte |
| PUT | `/api/v1/conflicts/{id}` | Actualitza un conflicte |
| DELETE | `/api/v1/conflicts/{id}` | Elimina un conflicte |

### Countries

| Mètode | Endpoint | Descripció |
|---|---|---|
| GET | `/api/v1/countries` | Llista tots els països |
| GET | `/api/v1/countries/{id}` | Obté un país concret |
| GET | `/api/v1/countries/code/{code}` | Cerca un país per codi |
| GET | `/api/v1/countries/{code}/conflicts` | Llista conflictes d'un país |

### Factions

| Mètode | Endpoint | Descripció |
|---|---|---|
| GET | `/api/v1/factions` | Llista totes les faccions |
| GET | `/api/v1/factions/{id}` | Obté una facció concreta |
| POST | `/api/v1/factions` | Crea una facció |
| PUT | `/api/v1/factions/{id}` | Actualitza una facció |
| DELETE | `/api/v1/factions/{id}` | Elimina una facció |
| POST | `/api/v1/factions/{factionId}/countries/{countryId}` | Afegeix un país a una facció |

### Events

| Mètode | Endpoint | Descripció |
|---|---|---|
| GET | `/api/v1/events` | Llista tots els esdeveniments |
| GET | `/api/v1/events/{id}` | Obté un esdeveniment concret |
| POST | `/api/v1/events` | Crea un esdeveniment |
| PUT | `/api/v1/events/{id}` | Actualitza un esdeveniment |
| DELETE | `/api/v1/events/{id}` | Elimina un esdeveniment |

---

## Com executar en local

### Requisits

- Java 17 o superior
- Maven o Maven Wrapper
- IntelliJ IDEA o un altre IDE compatible

### Execució

```sh
./mvnw spring-boot:run
```

En Windows:

```sh
mvnw.cmd spring-boot:run
```

L'API quedarà disponible a:

```text
http://localhost:8080/api/v1/conflicts
```

---

## Proves

Els endpoints s'han provat amb:

- Navegador
- IntelliJ HTTP Request
- Frontend Vue desplegat a Vercel

Exemple:

```http
GET https://conflicttracker-production-b460.up.railway.app/api/v1/conflicts
Accept: application/json
```

Resposta esperada:

```json
[
  {
    "id": 1,
    "name": "Ukraine War",
    "startDate": "2022-02-24",
    "status": "ACTIVE",
    "description": "Armed conflict between Ukraine and Russia"
  },
  {
    "id": 2,
    "name": "Cold War",
    "startDate": "1947-03-12",
    "status": "ENDED",
    "description": "Period of geopolitical tension between the USA and the Soviet Union"
  }
]
```

---

## Repositoris relacionats

Frontend:

```text
https://github.com/VictorMartosSegura/Conflict-monitor
```

Backend:

```text
https://github.com/VictorMartosSegura/ConflictTracker
```
