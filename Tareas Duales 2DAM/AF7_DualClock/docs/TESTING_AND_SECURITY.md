# Guía de Pruebas y Seguridad

## 1. Perfilado de Recursos (Android Studio Profiler)
Esta sección te guía en el uso del Android Studio Profiler para inspeccionar el uso de CPU, Memoria y Energía.

### Prerrequisitos
- Conecta un dispositivo físico o asegúrate de que tu emulador esté funcionando (se recomienda API 26+).
- Compila la aplicación en modo `debug`.

### Pasos
1.  **Lanzar Profiler**: En Android Studio, ve a **View > Tool Windows > Profiler**.
2.  **Iniciar Sesión**: Haz clic en el botón **+** en la ventana del Profiler y selecciona tu dispositivo y el proceso **DualClock**.
3.  **Inspeccionar CPU**:
    - Haz clic en la línea de tiempo de **CPU**.
    - Realiza acciones en la app (ej. Fichar entrada, Navegar).
    - Busca picos. Los picos altos pueden indicar trabajo pesado en el hilo principal (main thread).
4.  **Inspeccionar Memoria**:
    - Haz clic en la línea de tiempo de **Memory**.
    - Busca fugas de memoria (memory leaks) o asignaciones crecientes que no bajen después de eventos de recolección de basura.
    - Fuerza un GC (Garbage Collection / Recolección de basura) usando el icono de la papelera para ver si la memoria vuelve a su nivel base.
5.  **Inspeccionar Energía**:
    - Haz clic en la línea de tiempo de **Energy**.
    - Verifica que la app pase a uso "Light" (Ligero) o "None" (Nulo) cuando esté inactiva.

## 2. Seguridad (ProGuard / R8)
ProGuard (ahora R8) ofusca el código para dificultar la ingeniería inversa.

### Habilitar R8
R8 está habilitado por defecto para las compilaciones `release`. Para verificarlo:
1.  Abre `app/build.gradle.kts`.
2.  Asegúrate de que `isMinifyEnabled = true` esté configurado en el tipo de compilación `release`.

### Verificar la Ofuscación
1.  **Construir APK**: Ve a **Build > Build Bundle(s) / APK(s) > Build APK(s)**. Usa la variante **Release**.
2.  **Analizar APK**:
    - Ve a **Build > Analyze APK...**.
    - Selecciona el `app-release.apk` generado (generalmente en `app/build/outputs/apk/release/`).
3.  **Inspeccionar Clases**:
    - Navega a `classes.dex`.
    - Busca tu paquete `com.jose.dualclock`.
    - Al expandirlo debería mostrar nombres de clase ofuscados (ej. `a`, `b`, `c`) en lugar de `AttendanceRepositoryImpl`, o al menos miembros internos ofuscados si la clase en sí se mantiene.
    - Los componentes principales de Android (Activities) podrían mantenerse, pero la lógica interna debería renombrarse.

### Reglas
Si la app se cierra (crashea) en modo Release, revisa `proguard-rules.pro`. Es posible que necesites añadir reglas para evitar que ciertas clases sean ofuscadas, especialmente si se accede a ellas vía reflexión (ej. algunas librerías de DataStore o Serialización).