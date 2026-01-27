# 🎬 API de Gestión de Entradas de Cine

Este proyecto es una **API RESTful completa** desarrollada con **Spring Boot** para la simulación y gestión de un cine. El sistema permite administrar un catálogo de películas, directores, actores, salas y funciones, así como gestionar el flujo completo de **venta de entradas** con control de usuarios.

La aplicación implementa una arquitectura profesional orientada a servicios, utilizando **Docker** para la persistencia de datos y patrones de diseño avanzados (DTOs, Mappers) para garantizar la escalabilidad y evitar errores de recursividad.

---

## - Stack Tecnológico

El proyecto ha sido construido utilizando las siguientes tecnologías y herramientas:

* **Lenguaje:** Java 21 / 24
* **Framework:** Spring Boot 3.x (Web, Data JPA, Validation)
* **Base de Datos:** PostgreSQL 15 (Ejecutándose en Docker)
* **Mapeo de Objetos:** MapStruct 1.5.5.Final (Mapeo inteligente DTO-Entidad)
* **Reducción de Código:** Lombok
* **Gestión de Dependencias:** Maven
* **Cliente de Pruebas:** Postman

---

## - Arquitectura y Diseño del Sistema

El proyecto sigue los principios de **Clean Architecture** y separación de responsabilidades:

### 1. Capas de la Aplicación
* **Controller (`/controller`):** Maneja las peticiones HTTP y respuestas JSON. No contiene lógica de negocio.
* **Service (`/service`):** Contiene la lógica empresarial, validaciones (ej: verificar stock, calcular precios) y orquestación.
* **Repository (`/repository`):** Interfaz con la base de datos utilizando **Spring Data JPA**.
* **Mapper (`/mapper`):** Capa intermedia que transforma Entidades a DTOs y viceversa usando **MapStruct**.

### 2. Estrategia Anti-Bucles (Pattern DTO)
Para evitar el problema de referencia circular infinita (ej: Película -> Actor -> Película...) típico de JPA, implementamos **DTOs Estrictos**:
* **CreateDTO (Input):** Recibe datos planos y referencias por ID (ej: `directorId`, `salaId`).
* **ResponseDTO (Output):** Devuelve objetos estructurados unidireccionales.

### 3. Modelo de Datos (Relaciones)
* **Película - Director:** Relación 1:N.
* **Película - Actor:** Relación N:M (Tabla intermedia gestionada automáticamente).
* **Sala - Función:** Relación 1:N.
* **Usuario - Venta:** Relación 1:N.
* **Venta - Entrada:** Relación 1:N (Cascada).

---

## 🚀 Guía de Instalación y Ejecución

### 1. Requisitos
Tener instalado **Java JDK**, **Maven** y **Docker Desktop**.

### 2. Configuración de Base de Datos (Docker)
El proyecto incluye un archivo `compose.yaml` personalizado para evitar conflictos de puertos en entornos de desarrollo. Ejecuta en la terminal:

    docker-compose up -d

> **Nota Importante:** La base de datos se expone en el puerto externo **5433** (mapeado al interno 5432) para no chocar con otros PostgreSQL locales.
> * **Host:** `localhost`
> * **Port:** `5433`
> * **User:** `admin`
> * **Pass:** `password123`
> * **DB:** `cinedb`

### 3. Ejecutar la Aplicación
Iniciar la aplicación ejecutando la clase principal `EntradasApplication`.
* **Inicialización Automática:** Hibernate borrará y creará el esquema de base de datos desde cero (`ddl-auto=create`).

### 4. Carga de Datos Automática (Seeder)
Al iniciar, la clase `CineDataLoader` cargará automáticamente un escenario realista si la base de datos está vacía:
* **Películas:** *Inception, The Dark Knight, Pulp Fiction, The Godfather, Parasite*.
* **Salas:** *IMAX Laser, Dolby Atmos, VIP Experience, Standard*.
* **Usuarios Test:** *Pepe (ID 1), Maria (ID 2), Admin (ID 3)*.
* **Funciones:** Programación escalonada para pruebas inmediatas.

---

## 📡 Documentación de la API (Endpoints & JSONs)

A continuación, se detallan los endpoints disponibles y los **JSON Body** necesarios para probarlos en Postman.

### 🎥 1. Gestión de Películas (`/api/peliculas`)

* **GET** `/api/peliculas` - Listar catálogo completo.
* **POST** `/api/peliculas` - Registrar película.

  {
  "titulo": "Dune: Part Two",
  "duracion": 166,
  "edadMinima": 12,
  "directorId": 1,
  "actorIds": [1, 3]
  }

### 📅 2. Gestión de Funciones (`/api/funciones`)

* **GET** `/api/funciones` - Ver cartelera y horarios.
* **POST** `/api/funciones` - Programar sesión.

  {
  "fechaHora": "2026-03-20T18:00:00",
  "precio": 12.50,
  "peliculaId": 1,
  "salaId": 2
  }

### 👥 3. Usuarios (`/api/usuarios`)

* **GET** `/api/usuarios` - Listar clientes registrados.
* **POST** `/api/usuarios` - Registrar nuevo cliente.

  {
  "nombre": "Ana Garcia",
  "email": "ana@test.com",
  "password": "securePass123"
  }

### 🏟️ 4. Infraestructura (`/api/salas`)

* **GET** `/api/salas` - Consultar listado de salas y aforos.

### 🎟️ 5. Ventas y Entradas (`/api/ventas`)

* **GET** `/api/ventas/{id}` - Ver detalle del ticket y asientos.
* **POST** `/api/ventas` - **Comprar Entradas**.
    * *Lógica:* Calcula el total automáticamente basándose en el precio de la función y genera los tickets individuales.

  {
  "usuarioId": 2,
  "entradas": [
  {
  "funcionId": 1,
  "fila": 10,
  "asiento": 14
  },
  {
  "funcionId": 1,
  "fila": 10,
  "asiento": 15
  }
  ]
  }

---

## 📝 Diario de Desarrollo

Historial incremental de la construcción del proyecto:

1.  **Configuración del Entorno:**
    * Inicialización con Spring Initializr.
    * Configuración manual del `pom.xml` para resolver conflictos de compilación entre `lombok` y `mapstruct`.

2.  **Núcleo del Dominio:**
    * Creación de entidades base (`Pelicula`, `Director`, `Actor`) y sus repositorios.
    * Implementación de la capa Service para gestión de lógica.

3.  **Infraestructura Docker:**
    * Creación de `compose.yaml` para PostgreSQL.
    * **Solución de conflicto de puertos:** Migración del puerto estándar `5432` al `5433` en `application.properties` para permitir desarrollo concurrente.

4.  **Lógica de Negocio Avanzada:**
    * Implementación de `Sala` y `Funcion`.
    * Desarrollo del motor de ventas: `VentaService` realiza cálculos de importes, asignación de asientos y persistencia en cascada (`CascadeType.ALL`) para guardar Venta y Entradas en una sola transacción.

5.  **Data Seeding (Escenario Realista):**
    * Implementación de `CineDataLoader` (`CommandLineRunner`).
    * Carga automática de un catálogo de cine completo con 5 películas de culto, 5 salas diferenciadas y funciones programadas, permitiendo probar la API sin necesidad de insertar datos manualmente al inicio.

6.  **Validación y QA:**
    * Pruebas exhaustivas de endpoints mediante Postman.
    * Verificación de respuestas JSON limpias y Mappers correctos.

