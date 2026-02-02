

```
# Proyecto AF4: Gestión de Base de Datos Nativa XML (Simulación DOM)

Este proyecto implementa una aplicación en **Java** para la gestión de información utilizando **XML** como soporte de datos (Base de Datos Nativa XML simulada). Utiliza la API **Java DOM (Document Object Model)** para la lectura, escritura, modificación y eliminación de nodos, así como la gestión de colecciones (directorios) en el sistema de archivos.

## Descripción del Proyecto

El objetivo principal es demostrar el cumplimiento de los Criterios de Evaluación del **Resultado de Aprendizaje 5 (RA5)**:
1. **Análisis:** Uso de XML en entornos laborales.
2. **Manipulación:** CRUD completo sobre documentos XML (`productos.xml`).
3. **Colecciones:** Gestión lógica y física de carpetas y archivos.

## Requisitos Técnicos

* **Lenguaje:** Java (JDK 8 o superior).
* **Librerías:** Estándar de Java (`javax.xml.parsers`, `javax.xml.transform`, `org.w3c.dom`). No requiere dependencias externas.
* **Entorno:** Cualquier IDE (IntelliJ, Eclipse, NetBeans) o terminal.

## Instrucciones de Ejecución

1. Compilar la clase principal:

    javac org/AF4/GestionXML.java

2. Ejecutar la aplicación:

    java org.AF4.GestionXML

### Flujo de la aplicación:
* Al iniciar, el programa verifica si existe `productos.xml`. Si no, crea la estructura raíz `<tienda>` automáticamente.
* Se presenta un menú interactivo en consola para gestionar los productos.

---

## 1. Análisis de uso del formato XML (Respuesta a RA5 - Punto 1)

### Contextos de uso en la empresa real
El formato XML sigue siendo un estándar crítico en diversos sectores empresariales:

1. **Facturación Electrónica (FacturaE):**
   * *Contexto:* Intercambio legal de facturas entre empresas y la Administración Pública.
   * *Por qué XML:* Permite la firma digital (XML-DSig) y validación estricta de esquemas (XSD) para garantizar la integridad fiscal.
2. **Configuración de Software y Sistemas:**
   * *Contexto:* Archivos de despliegue como `pom.xml` (Maven), `web.xml` (Java EE) o configuraciones de frameworks.
   * *Por qué XML:* Estructura jerárquica clara y capacidad de definir atributos complejos anidados.
3. **Intercambio de Información (Web Services SOAP):**
   * *Contexto:* Comunicación entre sistemas bancarios, reservas de vuelos o sistemas médicos antiguos.
   * *Por qué XML:* El protocolo SOAP utiliza "sobres" XML para garantizar contratos de interfaz estrictos (WSDL).
4. **Feeds de Datos (RSS/Atom):**
   * *Contexto:* Sindicación de noticias y contenidos en medios de comunicación.

### Comparativa: XML vs Otros Formatos

| Característica | XML | JSON | CSV | SQL |
| :--- | :--- | :--- | :--- | :--- |
| **Legibilidad** | Alta (pero verboso) | Muy Alta (ligero) | Media (texto plano) | N/A (Consultas) |
| **Estructura** | Árbol jerárquico complejo | Mapas y listas | Tabular (filas/cols) | Relacional |
| **Validación** | Fuerte (DTD, XSD) | Débil (JSON Schema) | Nula | Muy Fuerte (Tipos BD) |
| **Uso Ideal** | Documentos formales, SOAP | APIs REST, Web, Apps | Cargas masivas datos | BBDD Transaccionales |

---

## 2. Creación y Manipulación (Detalle del Código)

La clase `GestionXML.java` implementa las siguientes funcionalidades requeridas para manipular el archivo `productos.xml`.

### Estructura del XML Generado

    <tienda>
        <producto id="1">
            <nombre>Portátil</nombre>
            <descripcion>Gaming</descripcion>
            <precio>1200</precio>
            <stock>10</stock>
        </producto>
    </tienda>

### Funcionalidades Implementadas (CRUD con DOM)
* **Insertar (RA5.g):** Genera IDs autoincrementales calculados dinámicamente y añade nodos hijos (`Element`) al documento raíz usando `doc.createElement` y `appendChild`.
* **Listar (RA5.e):** Recorre la `NodeList` de productos, extrae atributos e imprime el contenido de las etiquetas hijas.
* **Modificar (RA5.g):** Busca un producto por su atributo `id` y actualiza el contenido de texto del nodo `<precio>` usando `setTextContent`.
* **Eliminar (RA5.g):** Localiza el nodo por ID y utiliza `parentNode.removeChild(nodo)` para borrarlo físicamente del árbol.

---

## 3. Gestión de Colecciones (RA5.f)

El proyecto incluye una funcionalidad específica (**Opción 5 del menú**) para simular la organización lógica de archivos XML:

1. **Creación de Estructuras:**
   * Utiliza la clase `java.io.File`.
   * Genera una carpeta raíz llamada `coleccion_xml`.
   * Crea subdirectorios lógicos (por ejemplo, `2024` para organizar por año).
   * Inicializa archivos XML vacíos dentro de estas rutas.
2. **Mantenimiento:**
   * En un entorno real, estas colecciones permiten segmentar datos históricos (ej. `/facturas/2024/02/`).
   * El código demuestra cómo limpiar y borrar estas estructuras recursivamente para mantener el sistema limpio.

---

## Cumplimiento de Criterios (Checklist)

* [x] **RA5.b:** Instalación/Verificación de fichero físico.
* [x] **RA5.c:** Rutas configuradas como constantes.
* [x] **RA5.d:** Carga del DOM (`DocumentBuilder`).
* [x] **RA5.e:** Consultas y listado de nodos.
* [x] **RA5.f:** Creación y borrado de carpetas (Colecciones).
* [x] **RA5.g:** Insertar, Modificar y Eliminar nodos XML.

```