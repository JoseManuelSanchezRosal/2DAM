# 🎬 API de Gestión de Entradas de Cine

Este proyecto es una **API RESTful completa y segura** desarrollada con **Spring Boot** para la simulación y gestión de un cine. El sistema permite administrar un catálogo de películas, directores, actores, salas y funciones, así como gestionar el flujo completo de **venta de entradas** con control de usuarios y autenticación mediante **Tokens JWT**.

La aplicación implementa una arquitectura profesional orientada a servicios, utilizando **Docker** para la persistencia de datos, **Spring Security** para la protección de endpoints y patrones de diseño avanzados (DTOs, Mappers) para garantizar la escalabilidad y evitar errores de recursividad.

---

## 🛠 Stack Tecnológico

El proyecto ha sido construido utilizando las siguientes tecnologías y herramientas:

* **Lenguaje:** Java 21
* **Framework:** Spring Boot 3.4.x (Web, Data JPA, Validation)
* **Seguridad:** **Spring Security 6 + JWT (JSON Web Tokens)**
* **Base de Datos:** PostgreSQL 15 (Ejecutándose en Docker)
* **Mapeo de Objetos:** MapStruct (Mapeo inteligente DTO-Entidad)
* **Documentación:** **SpringDoc OpenAPI 2.8.4 (Swagger UI)**
* **Reducción de Código:** Lombok
* **Gestión de Dependencias:** Maven
* **Cliente de Pruebas:** Postman / Swagger UI

---

## 🏗 Arquitectura y Diseño del Sistema

El proyecto sigue los principios de **Clean Architecture** y separación de responsabilidades:

### 1. Capas de la Aplicación

* **Controller (`/controller`):** Maneja las peticiones HTTP y respuestas JSON. No contiene lógica de negocio.
* **Service (`/service`):** Contiene la lógica empresarial, validaciones (ej: verificar stock, calcular precios) y orquestación.
* **Repository (`/repository`):** Interfaz con la base de datos utilizando **Spring Data JPA**.
* **Mapper (`/mapper`):** Capa intermedia que transforma Entidades a DTOs y viceversa usando **MapStruct**.
* **Config (`/config`):** Configuración de seguridad (`SecurityFilterChain`), Swagger y Beans globales.

### 2. Seguridad (JWT & Spring Security)

Se ha implementado un sistema de seguridad **Stateless** (sin estado):
1.  **Login:** El usuario envía credenciales y recibe un `Bearer Token`.
2.  **Filtro JWT:** `JwtAuthenticationFilter` intercepta cada petición para validar la firma y expiración del token.
3.  **Protección de Rutas:**
    * 🔓 **Público:** Catálogo de películas, funciones y Swagger UI.
    * 🔒 **Privado:** Comprar entradas, ver tickets y gestión de usuarios.
4.  **Encriptación:** Contraseñas almacenadas con `BCryptPasswordEncoder`.

### 3. Estrategia Anti-Bucles (Pattern DTO)

Para evitar referencias circulares infinitas (ej: Película -> Actor -> Película...), implementamos **DTOs Estrictos**:
* **CreateDTO (Input):** Recibe datos planos y referencias por ID (ej: `directorId`, `salaId`).
* **ResponseDTO (Output):** Devuelve objetos estructurados unidireccionales.

---

## 🚀 Guía de Instalación y Ejecución

### 1. Requisitos

Tener instalado **Java JDK 21**, **Maven** y **Docker Desktop**.

### 2. Configuración de Base de Datos (Docker)

El proyecto incluye un archivo `compose.yaml` personalizado para evitar conflictos de puertos. Ejecuta en la terminal:

    docker-compose up -d

**Nota Importante:** La base de datos se expone en el puerto externo **5433** (mapeado al interno 5432) para no chocar con otros PostgreSQL locales.

* **Host:** `localhost`
* **Port:** `5433`
* **User:** `admin`
* **Pass:** `password123`
* **DB:** `cinedb`

### 3. Ejecutar la Aplicación

Iniciar la aplicación ejecutando la clase principal `EntradasApplication`.
* **Inicialización Automática:** Hibernate borrará y creará el esquema de base de datos desde cero (`ddl-auto=create`).

### 4. Carga de Datos Automática (Seeder)

Al iniciar, la clase `CineDataLoader` cargará automáticamente un escenario realista.

**Credenciales de prueba:**

| Usuario | Email | Password | Rol |
| :--- | :--- | :--- | :--- |
| **Pepe** | `pepe@cine.com` | `1234` | User |
| **Admin** | `admin@cine.com` | `admin` | Admin |

---

## 📘 Documentación Visual (Swagger UI)

Una vez iniciada la app, accede a la documentación interactiva:

👉 **`http://localhost:8080/swagger-ui/index.html`**

* Incluye el botón **"Authorize"** 🔓.
* Debes loguearte, copiar el token y pegarlo allí para probar los endpoints protegidos.

---

## 📡 Documentación de la API (Endpoints & JSONs)

### 🔐 0. Autenticación (Auth)

**POST** `/api/auth/login`
*Paso obligatorio para obtener el token.*

    {
      "email": "pepe@cine.com",
      "password": "1234"
    }

### 🎥 1. Gestión de Películas (`/api/peliculas`) [PÚBLICO]

* **GET** `/api/peliculas` - Listar catálogo completo.
* **POST** `/api/peliculas` - Registrar película.

    { 
      "titulo": "Dune: Part Two", 
      "duracion": 166, 
      "edadMinima": 12, 
      "directorId": 1, 
      "actorIds": [1, 3] 
    }

### 📅 2. Gestión de Funciones (`/api/funciones`) [PÚBLICO]

* **GET** `/api/funciones` - Ver cartelera y horarios.
* **POST** `/api/funciones` - Programar sesión.

    { 
      "fechaHora": "2026-03-20T18:00:00", 
      "precio": 12.50, 
      "peliculaId": 1, 
      "salaId": 2 
    }

### 👥 3. Usuarios (`/api/usuarios`) [PROTEGIDO 🔒]

* **GET** `/api/usuarios` - Listar clientes registrados.
* **POST** `/api/usuarios` - Registrar nuevo cliente (password encriptada).

    { 
      "nombre": "Ana Garcia", 
      "email": "ana@test.com", 
      "password": "securePass123" 
    }

### 🎟️ 5. Ventas y Entradas (`/api/ventas`) [PROTEGIDO 🔒]

* **GET** `/api/ventas/{id}` - Ver detalle del ticket y asientos.
* **POST** `/api/ventas` - **Comprar Entradas**.

**Lógica:** Calcula el total automáticamente y valida disponibilidad. Si el asiento está ocupado, devuelve `409 Conflict`.

    { 
      "usuarioId": 1, 
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

1.  **Configuración del Entorno:** Inicialización con Spring Initializr y configuración de Maven (Lombok/Mapstruct).
2.  **Núcleo del Dominio:** Creación de entidades base, repositorios y servicios.
3.  **Infraestructura Docker:** Configuración de `compose.yaml` (Puerto 5433).
4.  **Lógica de Negocio Avanzada:** Desarrollo del motor de ventas (validación de asientos y persistencia en cascada).
5.  **Data Seeding:** Implementación de `CineDataLoader` para carga automática de datos realistas (Nolan, Tarantino, etc.).
6.  **Seguridad y Autenticación:** * Implementación de Spring Security + JWT.
    * Protección de endpoints y manejo de errores 403.
7.  **Documentación y QA:**
    * Integración de **Swagger UI** (`OpenApiConfig`).
    * Corrección de conflictos de versiones (SpringDoc).
    * Pruebas de integridad (Happy path, 404 Not Found, 409 Conflict).