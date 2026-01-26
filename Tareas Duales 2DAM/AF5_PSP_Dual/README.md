# AF5 Proyecto Dual PSP: Servicio REST Seguro

Este proyecto implementa una API REST para la gestión de productos, desarrollada con **Spring Boot 3** y asegurada mediante **Spring Security**, cumpliendo con los Resultados de Aprendizaje **RA4** y **RA5** del módulo de Programación de Servicios y Procesos.

## 1. Estructura y Tecnologías
* **Paquete Base:** `org.AF5_PSP_Dual_JMSR`
* **Tecnologías:** Java 21, Spring Boot 3.2.2, Maven.
* **Seguridad:** Spring Security (Autenticación Basic Auth en memoria).
* **Diseño:** Arquitectura MVC (Modelo-Vista-Controlador) con gestión de estado en memoria.

## 2. Evidencias de Evaluación (Justificación de Criterios)

### RA4 - Servicios en Red (API REST)
Hemos desarrollado la clase `ProductoController.java` para exponer el servicio:

* **(RA4.a, RA4.b, RA4.c) Uso de librerías y protocolos estándar:**
  Utilizamos `spring-boot-starter-web` para implementar el protocolo HTTP/1.1 de forma nativa. La serialización a JSON es automática gracias a la librería Jackson.

* **(RA4.e) Concurrencia y gestión de clientes:**
  El servicio se despliega sobre un contenedor Tomcat embebido que gestiona múltiples hilos simultáneamente.
    * *Implementación:* Hemos sustituido el cálculo de IDs basado en `size()` por un contador atómico (`contadorIds`) para evitar colisiones cuando múltiples clientes crean recursos a la vez.

* **(RA4.f) Disponibilidad:**
  El servicio valida su despliegue en el puerto `8080` y responde con códigos de estado HTTP correctos (`200 OK`, `201 Created`, `404 Not Found`).

**Endpoints implementados:**
* `GET /api/productos`: Listado público (rol User/Admin).
* `POST /api/productos`: Crear (Solo Admin).
* `PUT /api/productos/{id}`: Modificar (Solo Admin).
* `DELETE /api/productos/{id}`: Borrar (Solo Admin).

### RA5 - Seguridad y Protección
Hemos protegido la aplicación en la clase `SecurityConfig.java`:

* **(RA5.h) Principios de programación segura:**
  Ningún endpoint es público. Se fuerza la autenticación en todas las peticiones (`anyRequest().authenticated()`).

* **(RA5.k) Esquemas basados en roles:**
  Hemos definido dos roles diferenciados en memoria:
    * `USER`: Perfil de consulta.
    * `ADMIN`: Perfil de gestión completa.

* **(RA5.j) Control de acceso y Políticas:**
  Aplicamos el principio de mínimo privilegio. Las operaciones destructivas (`DELETE`, `PUT`) o de creación (`POST`) están blindadas exclusivamente para el rol ADMIN.

* **Validación de Datos:**
  Protegemos la integridad de la aplicación validando en el controlador que el nombre del producto no sea nulo ni vacío antes de procesarlo, devolviendo un `400 Bad Request` si los datos son inválidos.

## 3. Pruebas de Funcionamiento (Postman)

Se ha verificado el correcto funcionamiento del ciclo de vida CRUD y la seguridad:

1.  **Inserción (POST) como Admin:** El sistema genera un ID autoincremental correctamente (evitando duplicados tras borrados) y devuelve `201 Created`.
2.  **Protección (DELETE) como User:** El sistema rechaza la petición con `403 Forbidden`, demostrando que la capa de seguridad intercepta la llamada antes de llegar al controlador.
3.  **Persistencia en Memoria:** Los datos se mantienen consistentes durante la vida de la aplicación gracias a la gestión de listas en el controlador.

---
