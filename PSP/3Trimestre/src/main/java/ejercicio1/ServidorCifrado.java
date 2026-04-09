package ejercicio1;

import java.io.*;
import java.net.*;

public class ServidorCifrado {
    public static void main(String[] args) {
        // Puerto por el que el servidor estará "escuchando" las peticiones
        int puertoDeEscucha = 7001;

        // El ServerSocket es como la "centralita" que espera las llamadas de los clientes
        try (ServerSocket centralitaServidor = new ServerSocket(puertoDeEscucha)) {
            System.out.println("Servidor iniciado y esperando clientes en el puerto " + puertoDeEscucha + "...");

            // Bucle infinito: el servidor nunca se apaga, siempre está dispuesto a atender a otro cliente
            while (true) {
                try (
                        // accept() detiene la ejecución aquí hasta que un cliente se conecta.
                        // Cuando alguien se conecta, crea un Socket específico para hablar solo con ese cliente.
                        Socket conexionConCliente = centralitaServidor.accept();

                        // Canal para ENVIAR respuestas al cliente conectado
                        PrintWriter canalSalida = new PrintWriter(conexionConCliente.getOutputStream(), true);

                        // Canal para RECIBIR los mensajes del cliente conectado
                        BufferedReader canalEntrada = new BufferedReader(new InputStreamReader(conexionConCliente.getInputStream()))
                ) {

                    System.out.println("\n--- Un nuevo cliente se ha conectado ---");

                    // PASO 1: El servidor da el primer paso y pregunta qué se desea hacer
                    canalSalida.println("Indica que quieres hacer (CIFRAR o DESCIFRAR):");

                    // PASO 2: El servidor se queda esperando la respuesta del cliente
                    String operacionSolicitada = canalEntrada.readLine();
                    System.out.println("El cliente ha elegido la operacion: " + operacionSolicitada);

                    // Nos aseguramos de que el cliente realmente envió algo (no se desconectó de golpe)
                    if (operacionSolicitada != null) {

                        // PASO 3: Le pedimos el texto sobre el que vamos a trabajar
                        canalSalida.println("DAME MENSAJE");

                        // PASO 4: Leemos el texto que el cliente nos acaba de mandar
                        String textoRecibido = canalEntrada.readLine();
                        System.out.println("Texto recibido del cliente: " + textoRecibido);

                        // PASO 5: Realizamos el trabajo pesado.
                        // Llamamos a nuestro método auxiliar pasando la operación, el desplazamiento (3) y el texto.
                        String resultadoProcesado = aplicarCifradoCesar(operacionSolicitada, 3, textoRecibido);

                        // Enviamos el resultado ya convertido de vuelta al cliente
                        canalSalida.println(resultadoProcesado);
                        System.out.println("Resultado enviado al cliente: " + resultadoProcesado);
                    }

                } catch (IOException excepcionComunicacion) {
                    // Si falla la comunicación con UN cliente en particular, entra aquí,
                    // pero el servidor sigue vivo para el siguiente gracias al bucle while(true).
                    System.err.println("Hubo un error hablando con el cliente: " + excepcionComunicacion.getMessage());
                }
            }
        } catch (IOException excepcionServidor) {
            // Este error ocurre si, por ejemplo, el puerto 7001 ya está siendo usado por otro programa
            System.err.println("No se pudo iniciar el servidor en el puerto " + puertoDeEscucha);
            excepcionServidor.printStackTrace();
        }
    }

    /**
     * Método auxiliar que realiza la transformación del texto (Cifrado César).
     * Es autodescriptivo y maneja tanto el cifrado como el descifrado.
     */
    private static String aplicarCifradoCesar(String tipoOperacion, int claveDesplazamiento, String textoOriginal) {
        // Usamos StringBuilder porque es más eficiente para construir textos letra por letra
        StringBuilder textoTransformado = new StringBuilder();
        int saltosTotales = 0;

        // Evaluamos qué operación pidió el cliente (ignorando mayúsculas/minúsculas)
        switch (tipoOperacion.toUpperCase()) {
            case "CIFRAR":
                // Para cifrar, avanzamos letras (positivo)
                saltosTotales = claveDesplazamiento;
                break;
            case "DESCIFRAR":
                // Para descifrar, retrocedemos letras (negativo)
                saltosTotales = -claveDesplazamiento;
                break;
            default:
                return "ERROR: Operación no reconocida. Debes indicar CIFRAR o DESCIFRAR.";
        }

        /* * Ajuste matemático: Aseguramos que los saltos siempre estén entre 0 y 25.
         * Esto arregla el problema de cuando intentamos "descifrar" y tenemos números negativos.
         */
        saltosTotales = saltosTotales % 26;
        if (saltosTotales < 0) {
            saltosTotales = saltosTotales + 26;
        }

        // Recorremos el texto original letra por letra
        for (int i = 0; i < textoOriginal.length(); i++) {
            char letraActual = textoOriginal.charAt(i);

            // Si es una letra mayúscula (A-Z)
            if (Character.isUpperCase(letraActual)) {
                /*

                 * 1. (letraActual - 'A') convierte la letra en un número de 0 a 25.
                 * 2. Le sumamos los saltos.
                 * 3. El % 26 asegura que si nos pasamos de la Z, volvamos a la A.
                 * 4. Sumamos 'A' de nuevo para volver a convertir el número en un carácter ASCII.
                 */
                char nuevaLetra = (char) ('A' + (letraActual - 'A' + saltosTotales) % 26);
                textoTransformado.append(nuevaLetra);

                // Si es una letra minúscula (a-z)
            } else if (Character.isLowerCase(letraActual)) {
                // Hacemos exactamente lo mismo, pero tomando la 'a' minúscula como base
                char nuevaLetra = (char) ('a' + (letraActual - 'a' + saltosTotales) % 26);
                textoTransformado.append(nuevaLetra);

                // Si es un espacio, una coma, un número, etc.
            } else {
                // No lo modificamos, lo añadimos tal cual
                textoTransformado.append(letraActual);
            }
        }
        // Convertimos el StringBuilder de vuelta a un String normal y lo devolvemos
        return textoTransformado.toString();
    }
}