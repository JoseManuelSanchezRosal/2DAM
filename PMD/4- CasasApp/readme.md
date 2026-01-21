# 🏠 YourHome

[cite_start]**YourHome** es una aplicación móvil nativa desarrollada en **Kotlin** y **Android Studio** que permite la gestión integral de un catálogo de viviendas (Venta y Alquiler)[cite: 18]. [cite_start]Diseñada con un enfoque moderno utilizando **Jetpack Compose** y siguiendo estrictamente el patrón de arquitectura **MVVM**[cite: 19].

## 📱 Descripción

Esta aplicación ofrece una solución completa para inmobiliarias o gestores de propiedades, permitiendo crear, editar, visualizar y eliminar anuncios de viviendas. [cite_start]Su diseño se centra en la experiencia de usuario (UX), incorporando temas dinámicos que cambian la paleta de colores de la aplicación según el tipo de transacción (Venta o Alquiler)[cite: 118, 119].

## ✨ Características Principales

* [cite_start]**Gestión CRUD Completa:** Creación, lectura, actualización y borrado seguro de inmuebles con diálogos de confirmación[cite: 112, 123].
* **Identidad Visual Dinámica ("Efecto Camaleón"):**
    * [cite_start]🟠 **Modo Venta:** La interfaz adopta tonos anaranjados cálidos[cite: 119].
    * [cite_start]🔵 **Modo Alquiler:** La interfaz cambia automáticamente a tonos azules fríos[cite: 120].
* **Gestión Avanzada de Imágenes:**
    * [cite_start]Soporte para múltiples fotografías por vivienda (carrusel)[cite: 113].
    * [cite_start]Integración de **Coil** para carga asíncrona y eficiente de memoria[cite: 114].
    * [cite_start]Persistencia real: Copia física de archivos al almacenamiento interno (`filesDir`) para evitar pérdida de URIs[cite: 116].
* [cite_start]**Buscador Inteligente:** Filtrado en tiempo real por título y switch rápido entre catálogos de venta y alquiler[cite: 121, 122].
* [cite_start]**Diseño Consistent (Material Design 3):** Interfaz unificada con radios de curvatura estandarizados (16dp) y componentes adaptativos como `FlowRow` para las características[cite: 125, 127].

## 🛠️ Stack Tecnológico

* **Lenguaje:** Kotlin
* **UI:** Jetpack Compose (Material Design 3)
* [cite_start]**Navegación:** Navigation Compose (NavHost) [cite: 21]
* **Arquitectura:** MVVM (Model - View - ViewModel)
* [cite_start]**Persistencia de Datos:** Room Database (SQLite Abstraction) [cite: 84]
* [cite_start]**Carga de Imágenes:** Coil (io.coil-kt) [cite: 114]
* [cite_start]**Asincronía:** Corrutinas & Flow [cite: 104]

## 🏛️ Arquitectura

[cite_start]La aplicación sigue una arquitectura limpia para separar la lógica de negocio de la interfaz[cite: 19]:

### 1. Capa de Datos (Room)
* **Entidad (`Casa.kt`):** Define la estructura de la tabla. [cite_start]Incluye `TypeConverters` para convertir listas de imágenes en cadenas de texto planas compatibles con SQLite[cite: 86, 91].
* **DAO (`CasaDao.kt`):** Interfaz de acceso a datos. [cite_start]Retorna flujos reactivos (`Flow<List<Casa>>`) para actualizaciones en tiempo real de la UI[cite: 94, 100].

### 2. Capa de Presentación (ViewModel)
* **ViewModel (`CasaViewModel.kt`):** Actúa como intermediario. [cite_start]Utiliza `viewModelScope.launch` y `Dispatchers.IO` para realizar operaciones de base de datos sin congelar el hilo principal (ANR prevention)[cite: 102, 104].

## 🗺️ Navegación

[cite_start]El flujo de la aplicación consta de 4 pantallas principales[cite: 14]:

1.  [cite_start]**Inicio:** Punto de entrada con opciones para explorar catálogo o publicar propiedad[cite: 65].
2.  **Catálogo:** Listado de `Cards` con foto, precio y dirección. [cite_start]Permite filtrar y buscar[cite: 70].
3.  **Formulario:** Pantalla reutilizable para crear y editar viviendas. [cite_start]Incluye selectores de características y gestión de fotos[cite: 75].
4.  [cite_start]**Detalle:** Vista completa del anuncio con carrusel de imágenes y lista de características[cite: 79].

## 📸 Capturas de Pantalla

*(Espacio reservado para insertar capturas de las pantallas: Inicio, Catálogo, Formulario y Detalle)*

## 👤 Autor

[cite_start]**José Manuel Sánchez Rosal** [cite: 1]
[cite_start]*2º DAM - IES Antonio Gala* [cite: 8, 9]
[cite_start]*Palma del Río (Córdoba)* [cite: 10]
