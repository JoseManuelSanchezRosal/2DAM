<div align="center">

# 🗄️ AF3: GESTIÓN DE BASES DE DATOS RELACIONALES (AaD y DIN)

**Proyecto para el Resultado de Aprendizaje 2 (RA2)**
*Persistencia de datos en Java con SQLite y JDBC*

![Java](https://img.shields.io/badge/Java-JDK_21-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![SQLite](https://img.shields.io/badge/Database-SQLite-003B57?style=for-the-badge&logo=sqlite&logoColor=white)
![JDBC](https://img.shields.io/badge/Driver-JDBC-red?style=for-the-badge)
![Maven](https://img.shields.io/badge/Build-Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

</div>

---

## 📖 Descripción del Proyecto

Este proyecto consiste en una aplicación de consola que implementa un sistema completo de persistencia de datos. El objetivo es simular la gestión de un inventario profesional, demostrando la capacidad de conectar, manipular y asegurar la integridad de la información mediante una base de datos relacional embebida (**SQLite**).

### 🎯 Objetivos (RA2)
* **Conexión:** Establecer comunicación entre Java y el SGBD.
* **Manipulación:** Operaciones CRUD (Create, Read, Update, Delete).
* **Integridad:** Gestión de transacciones (Commit/Rollback).

---

## ⚙️ Stack Tecnológico

| Componente | Tecnología | Descripción |
| :--- | :--- | :--- |
| **Lenguaje** | Java (JDK 21) | Lógica de la aplicación. |
| **Base de Datos** | SQLite | BBDD relacional ligera y embebida. |
| **Driver** | `sqlite-jdbc` | Puente de conexión JDBC. |
| **Gestión** | Maven | Gestión de dependencias y construcción. |

---

## 🏢 1. Análisis del Entorno Profesional

*Reflexión sobre la importancia de migrar a Bases de Datos Relacionales.*

### 📉 Situación "Legacy" (El Problema)
En muchas empresas, procesos críticos como la **Gestión de Pedidos** o el **Inventario de Almacén** se gestionan de forma precaria:
* ❌ Hojas de Excel compartidas en red (riesgo de corrupción).
* ❌ Envío de datos por correo electrónico (desincronización).
* ❌ Software legado obsoleto.

### 📈 La Solución Relacional (Ventajas)
Implementar este sistema con SQLite/SQL aporta:
1.  **Integridad de Datos:** Restricciones que evitan duplicados y errores de tipo.
2.  **Concurrencia:** Múltiples usuarios accediendo sin bloquear o corromper el archivo global.
3.  **Seguridad y Potencia:** Consultas complejas (filtros, joins) y control de acceso.

---

## 💻 2. Implementación Técnica (CRUD)

La aplicación cumple los requisitos mediante las siguientes funcionalidades conectadas a `jdbc:sqlite:tienda.db`:

* **🔌 Conexión:** Uso de `DriverManager` para conectar a la BBDD embebida.
* **🏗️ Estructura:** Script `CREATE TABLE IF NOT EXISTS` para inicializar la tabla `productos`.

### Operaciones Disponibles
* **Insertar (`INSERT`):** Alta de nuevos productos (Nombre, Stock, Precio).
* **Consultar (`SELECT`):** Listado formateado recuperando el `ResultSet`.
* **Actualizar (`UPDATE`):** Modificación del stock de una referencia existente.
* **Eliminar (`DELETE`):** Borrado físico de registros por ID.

---

## 🔄 3. Gestión de Transacciones (Atomicidad)

Se incluye una simulación avanzada en la **Opción 5** del menú (`simularVentaTransaccional`) para asegurar la consistencia de los datos.

### Mecanismo Implementado
1.  **Inicio:** Se desactiva el guardado automático: `conn.setAutoCommit(false)`.
2.  **Operaciones:** Se ejecutan varias instrucciones SQL (ej. insertar venta, descontar stock).
3.  **Validación:**
    * ✅ **Si todo va bien:** Se ejecuta `conn.commit()` (Los cambios se hacen permanentes).
    * ❌ **Si hay error:** Se ejecuta `conn.rollback()` (Se deshacen todos los cambios, volviendo al estado original).

> **Caso de uso real:** Vital en facturación. Si cobras a un cliente pero falla la actualización de stock, el sistema debe deshacer el cobro para evitar descuadres financieros.

---

## 📄 4. Reflexión: XML vs SQL vs JSON

Aunque este proyecto usa SQL, analizamos su relación con otros formatos en la empresa:

| Formato | Uso Principal | Ventaja | Desventaja |
| :--- | :--- | :--- | :--- |
| **SQL** | Base de Datos Transaccional | Integridad y Potencia de consulta | Requiere esquema rígido |
| **XML** | FacturaE, Configuración (Maven), SOAP | Validación fuerte (XSD) y Estándar legal | Muy verboso (pesado) |
| **JSON** | APIs REST, NoSQL, Web | Ligero y rápido de parsear | Menor validación nativa |

---

## ✅ Checklist de Evaluación (RA2)

| Criterio | Descripción | Implementación en Código | Estado |
| :--- | :--- | :--- | :---: |
| **RA2.b** | Uso de SGBD embebido | Librería `sqlite-jdbc` / Archivo `tienda.db` | ✅ |
| **RA2.c** | Conector idóneo | Importación `java.sql.*` y Driver Manager | ✅ |
| **RA2.d** | Establecer conexión | `DriverManager.getConnection(URL)` | ✅ |
| **RA2.e** | Definir estructura | Método `crearTablaProductos` (DDL) | ✅ |
| **RA2.f** | Modificar contenido | Métodos para Insertar, Actualizar y Borrar | ✅ |
| **RA2.g** | Almacenar resultado | Iteración sobre objetos `ResultSet` | ✅ |
| **RA2.h** | Efectuar consultas | Método `listarProductos` (SELECT) | ✅ |
| **RA2.j** | Transacciones | Control de `COMMIT` y `ROLLBACK` manual | ✅ |

---
