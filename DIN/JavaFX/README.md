# FridgeApp - Frigorífico Inteligente (JavaFX)

Proyecto Maven JavaFX (JDK 24 + JavaFX 21) preparado para abrir en NetBeans.

Cómo abrir:
1. Importa el proyecto Maven (`pom.xml`) en NetBeans (File → Open Project... → seleccionar la carpeta).
2. Ejecuta con la configuración por defecto. Si tu NetBeans no detecta JavaFX, usa:
   mvn javafx:run

Si tu entorno necesita configurar VM options para JavaFX con módulos, puedes ejecutar:
--module-path /path/to/javafx-sdk-21/lib --add-modules javafx.controls,javafx.fxml

Estructura:
- src/main/java/fridgeapp/... (código Java)
- src/main/resources/fridgeapp/view (FXML y style.css)
