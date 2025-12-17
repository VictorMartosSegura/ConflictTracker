# ConflictTracker API

ConflictTracker és una aplicació web que permet consultar i gestionar informació sobre conflictes armats, països implicats, faccions i esdeveniments rellevants.

L’aplicació està pensada per ser fàcil de provar, fins i tot per persones sense coneixements d’informàtica.

---

## Com executar l’aplicació 

### Què necessites abans de començar
- Un ordinador amb **Java instal·lat (versió 17 o superior)**
- **IntelliJ IDEA** (programa per obrir el projecte)
- **Maven** (normalment ja ve integrat amb IntelliJ)

---

### 1️⃣ Obrir el projecte
1. Obre **IntelliJ IDEA**
2. Fes clic a **Open**
3. Selecciona la carpeta del projecte `ConflictTracker`

---

### 2️⃣ Executar l’aplicació
1. A IntelliJ, busca l’arxiu:
2. Fes clic dret sobre l’arxiu
3. Selecciona **Run**

A l’adreça: es mostra una pàgina web senzilla que carrega dades de l’API i mostra els conflictes en una taula.

Aquesta pàgina comprova que l’API funciona correctament.

---

## GET http://localhost:8080/api/v1/conflicts
Base de totes les peticions de l’API

Tots els endpoints de l’API comencen per: http://localhost:8080/api/v1

### 🔹 Conflict

| Mètode | URL completa | Què fa |
|------|-------------|-------|
| GET | `http://localhost:8080/api/v1/conflicts` | Mostra tots els conflictes |
| GET | `http://localhost:8080/api/v1/conflicts/1` | Mostra un conflicte concret |
| GET | `http://localhost:8080/api/v1/conflicts?status=ACTIVE` | Filtra conflictes per estat |
| POST | `http://localhost:8080/api/v1/conflicts` | Crear un conflicte |
| PUT | `http://localhost:8080/api/v1/conflicts/1` | Actualitzar un conflicte |
| DELETE | `http://localhost:8080/api/v1/conflicts/1` | Eliminar un conflicte |

---

### 🔹 Country

| Mètode | URL completa | Què fa |
|------|-------------|-------|
| GET | `http://localhost:8080/api/v1/countries` | Mostra tots els països |
| GET | `http://localhost:8080/api/v1/countries/1` | Mostra un país |
| GET | `http://localhost:8080/api/v1/countries/code/UKR` | Cerca un país pel seu codi |
| GET | `http://localhost:8080/api/v1/countries/UKR/conflicts` | Conflictes d’un país |

---

### 🔹 Faction

| Mètode | URL completa | Què fa |
|------|-------------|-------|
| GET | `http://localhost:8080/api/v1/factions` | Mostra totes les faccions |
| GET | `http://localhost:8080/api/v1/factions/1` | Mostra una facció |
| POST | `http://localhost:8080/api/v1/factions` | Crear una facció |
| PUT | `http://localhost:8080/api/v1/factions/1` | Actualitzar una facció |
| DELETE | `http://localhost:8080/api/v1/factions/1` | Eliminar una facció |
| POST | `http://localhost:8080/api/v1/factions/1/countries/1` | Afegir país a facció |

---

### 🔹 Event

| Mètode | URL completa | Què fa |
|------|-------------|-------|
| GET | `http://localhost:8080/api/v1/events` | Mostra tots els esdeveniments |
| GET | `http://localhost:8080/api/v1/events/1` | Mostra un esdeveniment |
| POST | `http://localhost:8080/api/v1/events` | Crear un esdeveniment |
| PUT | `http://localhost:8080/api/v1/events/1` | Actualitzar un esdeveniment |
| DELETE | `http://localhost:8080/api/v1/events/1` | Eliminar un esdeveniment |

---

## Proves dels endpoints

Els endpoints de l’API s’han provat utilitzant el plugin HttpRequest
d’IntelliJ IDEA Community Edition.

S’ha gravat un vídeo demostratiu on es pot veure el funcionament
dels principals endpoints (GET, POST, PUT i DELETE), així com
les relacions entre entitats.

Enllaç al vídeo:
https://drive.google.com/file/d/1K6ueMWPzvVRusCprs65dA0pm_O-uby4I/view?usp=drive_link


