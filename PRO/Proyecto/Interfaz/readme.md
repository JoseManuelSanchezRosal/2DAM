# Walkthrough: Aplicación Web Tuturno

## Resumen de la Implementación
El proyecto web *Tuturno* (diseñado para la gestión y reservas de un salón de belleza) se ha desarrollado utilizando HTML semántico, CSS puro de alto rendimiento y JavaScript funcional. Todo el código ha sido configurado en tu directorio `c:\Users\José\Desktop\2DAM\2DAM\2DAM\PRO\Proyecto\Interfaz`.

<br>

### Estructura de Archivos
Se crearon tres archivos principales para garantizar una base libre de dependencias y de alta personalización:

1. **[index.html](file:///c:/Users/José/Desktop/2DAM/2DAM/2DAM/PRO/Proyecto/Interfaz/index.html)**
   * **Header (Navegación):** Enlaces adaptados con selectores base (`#view-inicio`, `#view-servicios`, etc.) para simular una Single Page Application (SPA).
   * **Vistas (SPA):** La web se dividió en 4 bloques funcionales:
     * **Inicio:** Una introducción y mensajes de bienvenida.
     * **Servicios:** Un catálogo maquetado en Grid con todos los tratamientos del salón y sus descripciones/precios básicos.
     * **Zona Clientes:** Un panel dividido en dos columnas con Flexbox; a la izquierda los formularios (Acceso/Registro por pestañas) y a la derecha el calendario (con interacciones bloqueadas visualmente hasta que se simule inicio de sesión).
   * **Tipografía & Iconos:** Tipografía moderna *Outfit* y colección de iconos de *LineAwesome*.

2. **[style.css](file:///c:/Users/José/Desktop/2DAM/2DAM/2DAM/PRO/Proyecto/Interfaz/style.css)**
   * **Funcionalidad SPA:** Clases como `.view` (oculta por defecto) y `.active` (con animación de `fadeIn`) que permiten permutar de pantallas sin recarga.
   * **Sistema de Diseño y Tokens:** Uso intensivo de las variables CSS (`:root`) manteniendo la paleta de colores de salón (mármol blanco, oro metálico, y colores mate).
   * **Micro-interacciones:** Los botones, tarjetas y pestañas de Login/Registro presentan efectos para sentirse táctiles y dinámicos.

3. **[script.js](file:///c:/Users/José/Desktop/2DAM/2DAM/2DAM/PRO/Proyecto/Interfaz/script.js)**
   * **Navegación SPA:** Sistema dinámico que oculta y muestra los IDs (`view-inicio`, `view-servicios`, etc.) y maneja el estado activo del navbar.
   * **Pestañas de Formulario:** Sistema añadido para permutar dinámicamente entre el formulario de **Login** y el de **Registro**, alterando también los estilos nativos de la pestaña y los inputs que se muestran al vuelo.
   * **Calendario Demostrativo:** Construye dinámicamente un mes ("Octubre 2026"), y su botón "Confirmar Cita" ha sido visualmente desactivado.

<br>

## Interacción y Simulación de Funcionalidades
Dado el alcance frontend del proyecto, se ha implementado la técnica de simulación de interacción real (mediante alertas informativas en JavaScript) para dejar manifiesto el flujo completo del usuario:
* **Registro Simulativo:** Al rellenar los datos y hacer clic en Crear Cuenta, una alerta confirmará la creación exitosa y conmutará automáticamente a la pestaña de Iniciar Sesión.
* **Flujo de Acceso (Login):** Al presionar "Acceder e Iniciar Reserva", el sistema muestra una alerta de éxito simulando la conexión con base de datos, procede a mostrar los turnos horarios del calendario y habilita el botón dorado final de "Reservar Cita". 
* **Bloqueo Inteligente de Horarios:** Si se intenta interactuar con un horario antes de "iniciar sesión", el sistema captura el click y envía un mensaje educativo: "Debes iniciar sesión... para poder seleccionar un horario".
* **Catálogo Atractivo:** Al hacer clic sobre una tarjeta de tratamiento en la vista de *Servicios*, se mostrará un mensaje indicando que este flujo llevaría a pre-seleccionar dicho ítem en la pantalla de reservas.

<br>

## Validación
* El archivo `index.html` ha sido inspeccionado localmente.
* La adaptación móvil (*media queries*) fue incorporada para pantallas menores a 992px y 768px, apilando el contenido de las tarjetas de mármol.
* **Siguiente paso recomendado:** Abrir `c:\Users\José\Desktop\2DAM\2DAM\2DAM\PRO\Proyecto\Interfaz\index.html` haciendo doble clic desde el Explorador de archivos para vivir la experiencia visual y fluida.
