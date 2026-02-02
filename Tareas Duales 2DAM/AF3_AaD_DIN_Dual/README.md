\# AF3: Gestión de Bases de Datos Relacionales (RA2)



\## 📖 Descripción del Proyecto

Este proyecto consiste en una aplicación de consola desarrollada en \*\*Java\*\* que implementa la persistencia de datos utilizando una base de datos relacional (\*\*SQLite\*\*). El objetivo es demostrar la capacidad de conectar, gestionar y manipular datos desde un lenguaje de programación, asegurando la integridad mediante transacciones.



El sistema simula la gestión de inventario identificada en el entorno profesional, permitiendo operaciones CRUD (Create, Read, Update, Delete) completas.



\## ⚙️ Stack Tecnológico

\* \*\*Lenguaje:\*\* Java (JDK 21)

\* \*\*Base de Datos:\*\* SQLite (Base de datos embebida)

\* \*\*Driver:\*\* JDBC (sqlite-jdbc)

\* \*\*Herramienta de Construcción:\*\* Maven



---



\## 🏢 1. Análisis del Entorno Profesional

\*Reflexión sobre el uso de datos en la empresa actual.\*



\*\*Proceso Identificado:\*\*

En la empresa, se ha identificado el proceso de `\[EJEMPLO: Gestión de pedidos de clientes / Inventario de almacén]`.



\*\*Gestión Actual de los Datos:\*\*

Actualmente, estos datos se gestionan mediante `\[EJEMPLO: Hojas de cálculo de Excel compartidas en red / Correos electrónicos / Software legado]`.



\*\*Ventajas de la Migración a Base de Datos Relacional:\*\*

Implementar una BBDD relacional (como la de este proyecto) aportaría:

1\.  \*\*Integridad de datos:\*\* Evitar duplicidades y errores de formato.

2\.  \*\*Concurrencia:\*\* Permitir que varios usuarios accedan simultáneamente sin corromper el archivo.

3\.  \*\*Seguridad y Consultas:\*\* Capacidad de filtrar datos complejos mediante SQL y establecer permisos.



---



\## 💻 2. Implementación Técnica (Criterios RA2)



La aplicación cumple con los requisitos prácticos del RA2 mediante las siguientes funcionalidades:



1\.  \*\*Conexión (JDBC):\*\* Se establece conexión con `jdbc:sqlite:tienda.db`.

2\.  \*\*Estructura:\*\* Creación automática de la tabla `productos` si no existe.

3\.  \*\*Operaciones CRUD:\*\*

&nbsp;   \* \*\*Insertar:\*\* Añadir nuevos productos con stock y precio.

&nbsp;   \* \*\*Consultar:\*\* Listado formateado de todos los productos.

&nbsp;   \* \*\*Actualizar:\*\* Modificación del stock de un producto existente.

&nbsp;   \* \*\*Eliminar:\*\* Borrado de registros por ID.



---



\## 🔄 3. Gestión de Transacciones e Integridad



Se ha implementado una simulación de transacción en la opción 5 del menú (`simularVentaTransaccional`).



\*\*Mecanismo:\*\*

\* Se desactiva el `autoCommit` (`conn.setAutoCommit(false)`).

\* Se realizan operaciones de inserción.

\* Si ocurre un error lógico o de SQL, se ejecuta un `ROLLBACK` para deshacer los cambios y volver al estado consistente anterior.

\* Si todo es correcto, se ejecuta `COMMIT` para persistir los datos.



\*\*Aplicación en la Empresa:\*\*

En un entorno real, esto es vital para procesos como facturación o movimientos de stock: si se descuenta stock pero falla el cobro, el sistema debe deshacer el descuento de stock para evitar descuadres de inventario.



---



\## 📄 4. Reflexión sobre Formatos: XML vs Otros



\*\*Uso de XML en la Empresa:\*\*

El formato XML se utiliza frecuentemente para:

\* Facturación Electrónica (Formatos FacturaE).

\* Archivos de configuración de servidores o aplicaciones (ej. `pom.xml` en Maven).

\* Intercambio de datos entre sistemas heterogéneos (Web Services SOAP).



\*\*XML vs JSON/SQL/CSV:\*\*

\* \*\*Ventajas XML:\*\* Es muy descriptivo, estandarizado y valida esquemas complejos (XSD). Ideal para documentos legales como facturas.

\* \*\*Desventajas:\*\* Es más "verboso" y pesado que JSON. Para APIs REST modernas o bases de datos rápidas, se prefiere JSON por su ligereza o SQL por su potencia de consulta.



---



\## ✅ Cumplimiento de Criterios (Checklist)



| Criterio | Descripción | Implementación en Código |

| :--- | :--- | :--- |

| \*\*RA2.b\*\* | Uso de SGBD embebido | `sqlite-jdbc` / `tienda.db` |

| \*\*RA2.c\*\* | Conector idóneo | Uso de librerías `java.sql.\*` y driver JDBC |

| \*\*RA2.d\*\* | Establecer conexión | `DriverManager.getConnection(URL)` |

| \*\*RA2.e\*\* | Definir estructura | Método `crearTablaProductos` (CREATE TABLE) |

| \*\*RA2.f\*\* | Modificar contenido | Métodos `insertar`, `actualizar`, `eliminar` |

| \*\*RA2.g\*\* | Almacenar resultado | Uso de objetos `ResultSet` |

| \*\*RA2.h\*\* | Efectuar consultas | Método `listarProductos` (SELECT) |

| \*\*RA2.j\*\* | Transacciones | Método `simularVentaTransaccional` (Commit/Rollback) |

