# 📂 AF4: GESTIÓN DE ARCHIVOS Y COLECCIONES XML (RA5)

Proyecto de Acceso a Datos: Bases de Datos Nativas XML. Implementación de parser DOM en Java con persistencia en sistema de archivos.

## 📋 Descripción del Proyecto

Este proyecto implementa una herramienta de línea de comandos (CLI) para gestionar una base de datos simulada utilizando **XML nativo**. El objetivo es demostrar el dominio de la manipulación de documentos XML mediante la API **Java DOM**, cumpliendo con todos los criterios del **Resultado de Aprendizaje 5 (RA5)**.

## 🎯 Objetivos Cumplidos

* **Persistencia:** Los datos se guardan físicamente en `productos.xml`.
* **Atomicidad:** Manipulación de nodos (elementos y atributos) en memoria y volcado a disco.
* **Gestión:** Organización lógica de archivos mediante colecciones (directorios).

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

**1. Compilación**
Compila el código fuente desde la raíz del proyecto:

    javac org/AF4/GestionXML.java

**2. Ejecución**
Ejecuta la clase principal. Si el archivo XML no existe, el programa lo creará automáticamente.

    java org.AF4.GestionXML

**3. Uso del Menú**
El sistema desplegará un menú interactivo:
* **Opción 1-4:** Operaciones CRUD sobre productos.
* **Opción 5:** Demostración de gestión de carpetas (RA5.f).

---

## 📚 Análisis del Formato XML (RA5 - Punto 1)

### 🏢 ¿Dónde se usa XML hoy en día?
A pesar del auge de JSON, XML sigue siendo el estándar en contextos críticos:
* **Factura Electrónica (FacturaE):**
    * *Uso:* Intercambio tributario obligatorio entre empresas y el Estado.
    * *Ventaja:* Permite Firma Digital (XML-DSig) y validación estricta de estructura.
* **Servicios Web SOAP:**
    * *Uso:* Integraciones bancarias y sistemas de reservas (Amadeus, Sabre).
    * *Ventaja:* Contratos de interfaz estrictos (WSDL) y seguridad (WS-Security).
* **Configuración de Entornos:**
    * *Uso:* pom.xml (Maven), web.xml (Java EE), Android Manifest.
    * *Ventaja:* Representación jerárquica clara de dependencias complejas.

### 🆚 Comparativa Técnica

| Característica | 📄 XML | ⚡ JSON | 📊 CSV | 🗄️ SQL (Relacional) |
| :--- | :--- | :--- | :--- | :--- |
| **Estructura** | Árbol de etiquetas (Verboso) | Clave-Valor (Ligero) | Tabular (Plano) | Tablas relacionales |
| **Validación** | Nativa (XSD, DTD) | Externa (JSON Schema) | Inexistente | Estricta (Esquemas, Claves foráneas) |
| **Tipado** | Todo es texto (salvo XSD) | Cadenas, Números, Booleanos | Texto plano | Tipos de datos estrictos |
| **Uso Ideal** | Documentos legales, Configuración | APIs REST, Mobile, Web | Data Science, Excel | Transacciones complejas, Integridad referencial |

---

## 💻 Documentación del Código (RA5 - Punto 2)

La clase `GestionXML.java` centraliza toda la lógica.

### Estructura del Archivo `productos.xml`
El programa genera y mantiene la siguiente estructura DOM:

    <tienda>
        <producto id="1">
            <nombre>Portátil Gaming</nombre>
            <descripcion>MSI 16GB RAM</descripcion>
            <precio>1200.50</precio>
            <stock>5</stock>
        </producto>
    </tienda>

### Fragmentos de Código Clave (Proceso CRUD)

**Insertar un nuevo nodo (RA5.g):**

    Element producto = doc.createElement("producto");
    producto.setAttribute("id", id);
    crearElemento(doc, producto, "nombre", nom);
    root.appendChild(producto);

**Eliminar un nodo existente (RA5.g):**

    for (int i = 0; i < lista.getLength(); i++) {
        Element e = (Element) lista.item(i);
        if (e.getAttribute("id").equals(idBuscado)) {
            e.getParentNode().removeChild(e); 
            break;
        }
    }

---

## 📁 Gestión y Organización de Colecciones (RA5 - Punto 3)

El método `gestionarColecciones()` crea una estructura lógica de directorios (`coleccion_xml/2024/`) para demostrar la organización de ficheros en lotes.

### Mantenimiento en un entorno de trabajo real
En un entorno empresarial, estas colecciones no se crearían manualmente. Se implementarían procesos automatizados que generarían carpetas estructuradas por año, mes y departamento (ej: `/facturas/2024/10/Ventas/`).

Para mantener estas colecciones a largo plazo, se aplicarían **políticas de retención**: los archivos recientes se mantendrían en almacenamiento rápido (Hot Storage), mientras que los años anteriores se comprimirían y migrarían a servidores de respaldo (Cold Storage) para ahorrar costes. Además, se aplicarían permisos estrictos de solo lectura a los directorios históricos para garantizar la inmutabilidad de los datos.

---

## ✅ Checklist de Evaluación (Auto-test)

| Criterio | Descripción | Estado |
| :--- | :--- | :--- |
| **RA5.b** | Instalación/Verificación de fichero físico | ✅ |
| **RA5.c** | Configuración de rutas constantes | ✅ |
| **RA5.d** | Conexión y Carga (DocumentBuilder) | ✅ |
| **RA5.e** | Consultas y listado de nodos | ✅ |
| **RA5.f** | Gestión de Colecciones (Directorios) | ✅ |
| **RA5.g** | CRUD: Insertar, Modificar, Eliminar nodos | ✅ |