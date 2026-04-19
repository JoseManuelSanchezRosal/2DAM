

Este documento detalla el funcionamiento, las reglas y la arquitectura técnica del sistema de Trivia multijugador.

---

## 1. Reglas del Juego
* **Capacidad:** Hasta 10 jugadores simultáneos.
* **Identificación:** Cada jugador debe introducir un **Nick** al conectar.
* **Dinámica:** El juego consta de **5 preguntas**.
* **Formato:** Opciones A, B, C o D (insensible a mayúsculas/minúsculas).
* **Tiempo:** Dispones de **10 segundos** por pregunta.
* **Puntuación:**
   * Respuesta correcta: **+1 punto**.
   * Respuesta incorrecta o fuera de tiempo: **0 puntos**.
* **Ganador:** Se anuncia al jugador con mayor puntuación al finalizar las 5 rondas.

---

## 2. Funcionamiento (Paso a Paso)

### Paso 1: Iniciar el Servidor
Ejecuta la clase `Servidor.java`. Verás el mensaje: *"Servidor arrancado y esperando jugadores..."*.

### Paso 2: Conexión de Clientes
Cada jugador debe ejecutar `Cliente.java`.
1. La consola pedirá: *"Introduce tu nick para conectarte"*.
2. El servidor notificará la entrada: *"[CONEXIÓN] El usuario 'Nombre' se ha conectado"*.

### Paso 3: Inicio de Partida (Administrador)
Cuando todos los jugadores estén listos, el administrador debe escribir **START** en la consola del servidor. Esto cerrará la entrada a nuevos jugadores e iniciará la primera pregunta.

### Paso 4: Responder
Al aparecer la pregunta, escribe simplemente la letra de tu elección (**A, B, C o D**) y pulsa Enter. El servidor confirmará si la respuesta fue registrada.

### Paso 5: Resultados
Tras cada pregunta, se mostrará el **Ranking Actualizado** en todas las pantallas. Al final, se proclamará al ganador.

---

## 3. Explicación de Arquitectura y Clases

Para garantizar que el juego no se bloquee y funcione en tiempo real, se ha implementado la siguiente estructura:

### Servidor y Control
* **`Servidor.java`**: Gestiona la escucha de red (puerto 5001). Utiliza métodos `synchronized` para gestionar el estado de la partida sin errores de concurrencia.
* **`HiloComandoStart.java`**: Es un hilo independiente que vigila el teclado del servidor. Permite que el administrador inicie el juego sin bloquear la llegada de nuevos clientes.
* **`GameManager.java`**: Es el cerebro de la lógica. Controla los tiempos (15s), envía las preguntas a todos y decide quién ha ganado.

### Gestión de Jugadores
* **`ClienteHandler.java`**: Se crea una instancia por cada jugador. Se encarga de "escuchar" la red de ese cliente específico. Valida que la respuesta sea A-D y suma los puntos de forma segura.
* **`Pregunta.java`**: Clase modelo que estructura los datos (enunciado, opciones y solución).

### Cliente y Visualización
* **`Cliente.java`**: Gestiona la entrada de teclado del usuario y la conexión inicial.
* **`ReceptorMensajes.java`**: Hilo secundario en el cliente. Su función es "pintar" en pantalla los mensajes que llegan del servidor (preguntas y rankings) mientras el hilo principal del cliente espera a que el usuario escriba su respuesta.