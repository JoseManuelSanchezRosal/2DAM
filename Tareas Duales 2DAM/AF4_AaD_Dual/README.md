<div align="center">

# 📂 AF5 GESTIÓN DE ARCHIVOS Y COLECCIONES XML (RA5)

**Proyecto de Acceso a Datos: Bases de Datos Nativas XML**
*Implementación de parser DOM en Java con persistencia en sistema de archivos*



</div>

---

## 📋 Descripción del Proyecto

Este proyecto implementa una herramienta de línea de comandos (CLI) para gestionar una base de datos simulada utilizando **XML nativo**. El objetivo es demostrar el dominio de la manipulación de documentos XML mediante la API **Java DOM**, cumpliendo con todos los criterios del **Resultado de Aprendizaje 5 (RA5)**.

### 🎯 Objetivos Cumplidos
1.  **Persistencia:** Los datos se guardan físicamente en `productos.xml`.
2.  **Atomicidad:** Manipulación de nodos (elementos y atributos) en memoria y volcado a disco.
3.  **Gestión:** Organización lógica de archivos mediante colecciones (directorios).

---

## 🛠️ Requisitos y Stack Tecnológico

| Componente | Tecnología | Descripción |
| :--- | :--- | :--- |
| **Lenguaje** | Java (JDK 8+) | Lógica de negocio y control de flujo. |
| **Motor XML** | `org.w3c.dom` | Manipulación del árbol de nodos en memoria. |
| **Transformación** | `javax.xml.transform` | Serialización del DOM a archivo físico. |
| **I/O** | `java.io.File` | Gestión de carpetas y existencia de ficheros. |

---

## 🚀 Guía de Ejecución

Sigue estos pasos para compilar y probar la aplicación:

### 1. Compilación
Compila el código fuente desde la raíz del proyecto:

~~~bash
javac org/AF4/GestionXML.java
~~~

### 2. Ejecución
Ejecuta la clase principal. Si el archivo XML no existe, el programa lo creará automáticamente.

~~~bash
java org.AF4.GestionXML
~~~

### 3. Uso del Menú
El sistema desplegará un menú interactivo:
* **Opción 1-4:** Operaciones CRUD sobre productos.
* **Opción 5:** Demostración de gestión de carpetas (RA5.f).

---

## 📚 Análisis del Formato XML (RA5 - Punto 1)

### 🏢 ¿Dónde se usa XML hoy en día?
A pesar del auge de JSON, XML sigue siendo el estándar en contextos críticos:

1.  **Factura Electrónica (FacturaE):**
    * *Uso:* Intercambio tributario obligatorio entre empresas y el Estado.
    * *Ventaja:* Permite **Firma Digital (XML-DSig)** y validación estricta de estructura.
2.  **Servicios Web SOAP:**
    * *Uso:* Integraciones bancarias y sistemas de reservas (Amadeus, Sabre).
    * *Ventaja:* Contratos de interfaz estrictos (WSDL) y seguridad (WS-Security).
3.  **Configuración de Entornos:**
    * *Uso:* `pom.xml` (Maven), `web.xml` (Java EE), Android Manifest.
    * *Ventaja:* Representación jerárquica clara de dependencias complejas.

### 🆚 Comparativa Técnica

| Característica | 📄 XML | ⚡ JSON | 📊 CSV |
| :--- | :--- | :--- | :--- |
| **Estructura** | Árbol de etiquetas (Verboso) | Clave-Valor (Ligero) | Tabular (Plano) |
| **Validación** | **Nativa** (XSD, DTD) | Externa (JSON Schema) | Inexistente |
| **Tipado** | Todo es texto (a menos que use XSD) | Cadenas, Números, Booleanos | Texto plano |
| **Uso Ideal** | Documentos legales, Configuración | APIs REST, Mobile, Web | Data Science, Excel |

---

## 💻 Documentación del Código

La clase `GestionXML.java` centraliza toda la lógica. A continuación se detallan las estructuras clave.

### Estructura del Archivo `productos.xml`
El programa genera y mantiene la siguiente estructura DOM:

~~~xml
<tienda>
    <producto id="1">
        <nombre>Portátil Gaming</nombre>
        <descripcion>MSI 16GB RAM</descripcion>
        <precio>1200.50</precio>
        <stock>5</stock>
    </producto>
    <producto id="2">
        <nombre>Ratón Óptico</nombre>
        <descripcion>Logitech USB</descripcion>
        <precio>25.00</precio>
        <stock>100</stock>
    </producto>
</tienda>
~~~

### Métodos Clave (Mapping RA5)

* **`cargarDOM(File archivo)`**: Utiliza `DocumentBuilderFactory` para convertir el fichero físico en un objeto `Document` manipulable.
* **`guardarXML(Document doc, File archivo)`**: Utiliza `Transformer` para volcar los cambios de memoria al disco duro con indentación.
* **`gestionarColecciones()`**: (Opción 5) Crea una estructura de carpetas `coleccion_xml/2024/` para demostrar la organización de ficheros en lotes.

---

## ✅ Checklist de Evaluación (Auto-test)

| Criterio | Descripción | Estado |
| :--- | :--- | :---: |
| **RA5.b** | Instalación/Verificación de fichero físico | ✅ |
| **RA5.c** | Configuración de rutas constantes | ✅ |
| **RA5.d** | Conexión y Carga (`DocumentBuilder`) | ✅ |
| **RA5.e** | Consultas y listado de nodos | ✅ |
| **RA5.f** | Gestión de Colecciones (Directorios) | ✅ |
| **RA5.g** | CRUD: Insertar, Modificar, Eliminar nodos | ✅ |

---