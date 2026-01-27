\# 🎬 API de Gestión de Entradas de Cine



Este proyecto es una \*\*API RESTful completa\*\* desarrollada con \*\*Spring Boot\*\* para la gestión de un cine. Permite administrar películas, actores, directores, salas, funciones y realizar ventas de entradas con control de usuarios.



El sistema implementa una arquitectura robusta orientada a servicios, utilizando \*\*Docker\*\* para la base de datos y patrones de diseño avanzados para el mapeo de datos.



\## 🛠️ Stack Tecnológico



\* \*\*Lenguaje:\*\* Java 21

\* \*\*Framework:\*\* Spring Boot 4.0.2 (Web, Data JPA, Validation)

\* \*\*Base de Datos:\*\* PostgreSQL 15 (Contenerizada)

\* \*\*Infraestructura:\*\* Docker \& Docker Compose

\* \*\*Mapeo:\*\* MapStruct 1.5.5 (Mapeo DTO-Entidad inteligente)

\* \*\*Herramientas:\*\* Lombok, Maven

\* \*\*Cliente de Pruebas:\*\* Postman



\## 🏛️ Arquitectura y Patrones de Diseño



El proyecto sigue estrictamente las mejores prácticas de desarrollo backend:



1\.  \*\*Arquitectura por Capas:\*\*

&nbsp;   \* \*\*Controller:\*\* Solo gestiona peticiones HTTP. Cero lógica de negocio.

&nbsp;   \* \*\*Service:\*\* Contiene la lógica de negocio, validaciones y orquestación.

&nbsp;   \* \*\*Repository:\*\* Capa de acceso a datos (Spring Data JPA).



2\.  \*\*Patrón DTO (Data Transfer Object):\*\*

&nbsp;   \* \*\*Entrada (Request):\*\* Se reciben datos planos y referencias por ID (ej: `directorId`) para evitar inconsistencias.

&nbsp;   \* \*\*Salida (Response):\*\* Se devuelven objetos estructurados y ricos, pero unidireccionales para evitar \*\*bucles infinitos\*\* en el JSON.



3\.  \*\*Gestión de Relaciones:\*\*

&nbsp;   \* Relaciones 1:N (Director-Película, Sala-Función) y N:M (Película-Actor) gestionadas automáticamente mediante JPA y Servicios intermedios.



\## 🚀 Instalación y Ejecución



\### 1. Requisitos Previos

\* Java 21 instalado.

\* Docker Desktop instalado y corriendo.

\* Maven.



\### 2. Levantar la Base de Datos

El proyecto utiliza un archivo `compose.yaml` personalizado para evitar conflictos de puertos.



```bash

\# En la raíz del proyecto

docker-compose up -d

