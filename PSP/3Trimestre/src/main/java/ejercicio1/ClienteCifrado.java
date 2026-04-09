package ejercicio1;

import java.io.*;
import java.net.*;

public class ClienteCifrado {
    public static void main(String[] args) {
        // Definimos a dónde nos vamos a conectar.
        // "localhost" significa que el servidor está en este mismo ordenador.
        String direccionServidor = "localhost";
        // El puerto debe ser exactamente el mismo en el que el servidor está escuchando.
        int puertoServidor = 7001;

        /* * Usamos un bloque "try-with-resources" (los paréntesis después del try).
         * Esto asegura que las conexiones y los flujos de datos se cierren automáticamente
         * al terminar, incluso si ocurre un error, liberando así los recursos.
         */
        try (
                // 1. Creamos el "teléfono" para llamar al servidor
                Socket socketCliente = new Socket(direccionServidor, puertoServidor);

                // 2. Creamos el canal para ENVIAR mensajes (escribir) al servidor.
                // El 'true' significa que los datos se enviarán automáticamente (auto-flush) al usar println.
                PrintWriter canalSalida = new PrintWriter(socketCliente.getOutputStream(), true);

                // 3. Creamos el canal para RECIBIR mensajes (leer) desde el servidor.
                BufferedReader canalEntrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()))
        ) {

            // PASO 1: Leer la pregunta inicial del servidor
            // El método readLine() hace que el programa se pause hasta que reciba un mensaje.
            String preguntaDelServidor = canalEntrada.readLine();
            System.out.println("Servidor dice: " + preguntaDelServidor);

            // PASO 2: Responder al servidor con la operación elegida
            // Definimos qué queremos hacer y lo enviamos por nuestro canal de salida.
            String operacionElegida = "CIFRAR";
            System.out.println("Enviando orden al servidor: " + operacionElegida);
            canalSalida.println(operacionElegida);

            // PASO 3: Leer la solicitud de mensaje del servidor
            // El servidor ahora debería decirnos "DAME MENSAJE".
            String peticionDeMensaje = canalEntrada.readLine();
            System.out.println("Servidor dice: " + peticionDeMensaje);

            // PASO 4: Enviar el texto que queremos procesar
            // Preparamos nuestro mensaje secreto y lo mandamos.
            String textoAProcesar = "Hola Mundo, ataque al amanecer";
            System.out.println("Enviando texto al servidor: " + textoAProcesar);
            canalSalida.println(textoAProcesar);

            // PASO 5: Leer la respuesta final
            // Recibimos el texto ya cifrado (o descifrado) por el servidor y lo mostramos.
            String textoFinalProcesado = canalEntrada.readLine();
            System.out.println("Resultado final devuelto por el servidor: " + textoFinalProcesado);

        } catch (IOException excepcionEntradaSalida) {
            // Si el servidor no está encendido o hay un corte de red, capturamos el error aquí
            System.err.println("No se pudo conectar con el servidor. Detalle: " + excepcionEntradaSalida.getMessage());
        }
    }
}