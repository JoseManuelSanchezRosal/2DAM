```markdown
# Proyecto Dual AF4 AaD: Gestión de Datos (SQL y XML)

Este proyecto implementa una solución de gestión de datos para la asignatura de Acceso a Datos. Se abordan dos tecnologías principales de persistencia: **Bases de Datos Relacionales (SQLite)** y **Bases de Datos Nativas XML (DOM/Nativo)**.

El objetivo es demostrar la consecución de los Resultados de Aprendizaje **RA2** y **RA5** mediante una aplicación Java modular e interactiva.

## 📂 Estructura del Proyecto

El proyecto está organizado bajo el paquete `org.AF4` y consta de los siguientes módulos:

```text
src/main/java/org/AF4/
├── GestionBBDD.java  --> [RA2] Cliente SQL con menú interactivo (SQLite).
└── GestionXML.java   --> [RA5] Gestor XML nativo con menú interactivo (DOM).

Raíz del proyecto/
├── tienda.db         --> Base de datos SQLite generada automáticamente.
└── productos.xml     --> Base de datos XML generada automáticamente.

```

## 🚀 Instrucciones de Ejecución

1. **Requisitos**: Java JDK instalado y entorno IntelliJ IDEA.
2. **Dependencias**: El proyecto requiere el driver JDBC de SQLite (`sqlite-jdbc`).
3. **Ejecución**:
* Para probar la parte SQL: Ejecutar `GestionBBDD.main()`.
* Para probar la parte XML: Ejecutar `GestionXML.main()`.


4. **Interacción**: Ambas clases despliegan un menú por consola que permite realizar operaciones CRUD y gestión de archivos.

---

## ✅ Evidencias de Evaluación (Checklist de Criterios)

A continuación, se detalla cómo se cumple cada criterio de evaluación solicitado en el documento *AF4 AD dual.pdf*, indicando la clase y método donde se implementa.

### 🗄️ RA2 - Bases de Datos Relacionales (SQL)

| Criterio | Descripción | Implementación en Código (`GestionBBDD.java`) |
| --- | --- | --- |
| **RA2.a** | Valoración de conectores | Ver sección "Memoria Técnica" al final de este documento. |
| **RA2.b** | Gestores embebidos | Se utiliza **SQLite** (`jdbc:sqlite:tienda.db`), una BBDD serverless y embebida. |
| **RA2.c** | Conector idóneo | Uso de la librería JDBC específica para SQLite definida en `URL`. |
| **RA2.d** | Establecer conexión | `DriverManager.getConnection(URL)` dentro de un bloque *try-with-resources*. |
| **RA2.e** | Definir estructura | Método `crearTablaProductos()`. Ejecuta `CREATE TABLE IF NOT EXISTS`. |
| **RA2.f** | Modificar contenido | Métodos `insertarProducto()` (INSERT), `actualizarStock()` (UPDATE), `eliminarProducto()` (DELETE). |
| **RA2.g** | Objetos de resultado | Uso de la clase `ResultSet` para iterar datos en `listarProductos()`. |
| **RA2.h** | Efectuar consultas | Método `listarProductos()`. Ejecuta `SELECT * FROM`. |
| **RA2.i** | Cierre de recursos | Implementado automáticamente mediante `try (...)` (try-with-resources) para `Connection`, `Statement` y `ResultSet`. |
| **RA2.j** | Transacciones | Método `simularVentaTransaccional()`. Uso de `setAutoCommit(false)`, `commit()` y `rollback()`. |

### 📄 RA5 - Bases de Datos XML (Nativas)

| Criterio | Descripción | Implementación en Código (`GestionXML.java`) |
| --- | --- | --- |
| **RA5.a** | Valoración XML nativo | Ver sección "Memoria Técnica" al final de este documento. |
| **RA5.b** | Instalación del gestor | Uso del Sistema de Archivos nativo y la librería estándar `javax.xml` (DOM). No requiere instalación externa. |
| **RA5.c** | Configuración gestor | Definición de constantes y rutas de archivos (`NOMBRE_ARCHIVO`). |
| **RA5.d** | Establecer conexión | Método `cargarDOM()`. Carga el archivo en memoria usando `DocumentBuilder`. |
| **RA5.e** | Consultas contenido | Método `leerProductosXML()`. Itera nodos usando `getElementsByTagName()`. |
| **RA5.f** | Gestión colecciones | Método `gestionarColecciones()`. Crea y elimina directorios usando la clase `File`. |
| **RA5.g** | CRUD Documentos XML | Métodos `insertarProductoXML()` (Añadir nodo), `modificarPrecioXML()` (Modificar nodo). |

---

## 🧠 Memoria Técnica y Reflexión

### Reflexión sobre RA2 (Bases de Datos Relacionales)

**Análisis del entorno:** En la gestión empresarial, el uso de hojas de cálculo provoca errores de duplicidad e inconsistencia.
**Valoración (RA2.a):** La implementación de una BBDD Relacional mediante **JDBC** permite desacoplar la lógica de negocio del motor de base de datos.

* **Ventajas:** Si la empresa migra de SQLite a MySQL, solo es necesario cambiar la cadena de conexión y el driver, manteniendo el código Java casi intacto. Además, las **transacciones** (implementadas en el criterio RA2.j) garantizan la integridad de los datos financieros frente a fallos del sistema.

### Reflexión sobre RA5 (Bases de Datos XML)

**Análisis del entorno:** El formato XML es un estándar industrial para el intercambio de información (FacturaE, configuraciones, SOAP).
**Valoración (RA5.a):**

* **Ventajas:** Al usar XML nativo con **DOM**, obtenemos una estructura jerárquica y autodescriptiva, ideal para datos complejos que no encajan bien en tablas planas. Es un formato universal y legible por humanos.
* **Inconvenientes:** Frente a JSON, es más verboso (ocupa más espacio). Frente a SQL, el acceso es más lento ya que requiere parsear el documento completo en memoria.

```
