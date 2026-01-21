# 🏠 YourHome

**YourHome** es una aplicación móvil nativa desarrollada en **Kotlin** y **Android Studio** para la gestión integral de un catálogo de viviendas. El proyecto implementa una arquitectura robusta **MVVM** y un diseño de interfaz moderno con **Jetpack Compose**.

## 📱 Descripción General

La aplicación permite a los usuarios gestionar inmuebles (venta y alquiler) mediante un flujo de navegación fluido. Se ha puesto especial énfasis en la experiencia de usuario (UX) mediante el "Efecto Camaleón", que adapta la paleta de colores de la aplicación según el tipo de transacción.

## ✨ Características Principales

### 🎨 UI/UX y Diseño ("The Master Touch")
* **Identidad Visual Dinámica:** La interfaz cambia de color automáticamente:
    * 🟠 **Modo Venta:** Tonos anaranjados cálidos.
    * 🔵 **Modo Alquiler:** Tonos azules fríos.
* **Material Design 3:** Diseño consistente con radios de curvatura estandarizados (16dp) en tarjetas y botones.
* **Componentes Adaptables:** Uso de `FlowRow` para la visualización ordenada de características (etiquetas) sin scroll horizontal.

### 🛠 Funcionalidades Técnicas
* **Gestión de Imágenes Avanzada:**
    * Soporte para **múltiples fotografías** por vivienda (Carrusel).
    * Uso de **Coil** para carga asíncrona, caché y redimensionado eficiente.
    * **Persistencia Real:** Copia de seguridad de imágenes en el almacenamiento interno (`filesDir`) para evitar la pérdida de referencias (URIs).
* **Buscador Inteligente:** Filtrado en tiempo real por título y switch rápido entre Venta/Alquiler.
* **Seguridad:** Diálogos de confirmación (`AlertDialog`) antes de eliminar registros y sus archivos asociados.

## 🏗️ Arquitectura del Proyecto

La aplicación sigue estrictamente el patrón **MVVM (Model-View-ViewModel)** para separar la lógica de negocio de la interfaz gráfica.

### 1. Capa de Datos (Room Database)
Utiliza **Room** como capa de abstracción sobre SQLite:
* **Entidad (`Casa.kt`):** Define la estructura de la tabla. Incluye `TypeConverters` para almacenar listas de imágenes como cadenas de texto.
* **DAO (`CasaDao.kt`):** Gestiona las consultas SQL y expone flujos reactivos (`Flow<List<Casa>>`) para actualizaciones automáticas de la UI.

### 2. Capa de Presentación (ViewModel)
* **`CasaViewModel.kt`:** Intermediario entre la UI y la base de datos.
* **Corrutinas:** Ejecuta operaciones de base de datos en hilos secundarios (`Dispatchers.IO`) para prevenir bloqueos (ANR) y asegurar la fluidez de la app.

## 🗺️ Navegación

Implementada con **Navigation Compose (NavHost)**, estructurada en 4 destinos:
1.  **Inicio:** Pantalla de bienvenida con accesos directos.
2.  **Catálogo:** Lista de tarjetas con buscador y filtros.
3.  **Formulario:** Pantalla reutilizable para **Crear** y **Editar** inmuebles.
4.  **Detalle:** Vista completa con carrusel de fotos y lista de características.

## 👤 Autor

**José Manuel Sánchez Rosal**
2º DAM - IES Antonio Gala
