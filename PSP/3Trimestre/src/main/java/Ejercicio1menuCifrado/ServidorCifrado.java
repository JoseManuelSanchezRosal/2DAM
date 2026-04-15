package Ejercicio1menuCifrado;

import java.io.*;
import java.net.*;

public class ServidorCifrado {
    public static void main(String[] args) {
        System.out.println("Servidor arrancado y esperando...");

        try (
                // Creamos el socket del servidor en el puerto 1234
                ServerSocket server = new ServerSocket(1234);
                // Esperamos y aceptamos la conexión de un cliente
                Socket cliente = server.accept();
                // Flujo para recibir datos del cliente
                BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                // Flujo para enviar datos al cliente
                PrintWriter out = new PrintWriter(cliente.getOutputStream(), true);
        ) {
            String operacion;

            // Bucle principal: lee la operación enviada por el cliente
            // Se detiene si el cliente cierra la conexión o envía "salir"
            while ((operacion = in.readLine()) != null && !operacion.equalsIgnoreCase("salir")) {

                switch (operacion.toLowerCase()) {
                    case "cifrar":
                    case "descifrar":
                        // Solicitamos la cadena al cliente (paso intermedio del protocolo)
                        out.println("Pasame la cadena");
                        String cadenaACifrar = in.readLine();

                        // Procesamos la cadena y enviamos el resultado de vuelta
                        // Usamos un desplazamiento fijo de 3 (Cifrado César estándar)
                        String resultado = cifrar_descifrar(cadenaACifrar, operacion.toLowerCase(), 3);
                        out.println(resultado);
                        break;

                    default:
                        // Si la palabra no es válida, informamos al cliente
                        out.println("Operación no permitida. Escribe 'cifrar', 'descifrar' o 'salir'");
                        break;
                }
            }

            // Mensaje final de protocolo antes de cerrar la conexión
            out.println("ADIOS");

        } catch (IOException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
    }

    /**
     * Implementación del algoritmo César para cifrar o descifrar cadenas.
     * @param cadena Texto de entrada.
     * @param operacion "cifrar" o "descifrar".
     * @param desplazamiento Número de posiciones a mover en el alfabeto.
     * @return El texto procesado.
     */
    public static String cifrar_descifrar(String cadena, String operacion, int desplazamiento) {
        if (cadena == null) return "";

        int rango = ('z' - 'a') + 1; // Tamaño del alfabeto (26)
        StringBuilder resultado = new StringBuilder();
        char c;

        switch (operacion.toLowerCase()) {
            case "cifrar":
                for (int i = 0; i < cadena.length(); i++) {
                    c = cadena.charAt(i);
                    // Lógica para minúsculas
                    if (c >= 'a' && c <= 'z') {
                        resultado.append((char) (((c - 'a') + desplazamiento) % rango + 'a'));
                    }
                    // Lógica para mayúsculas
                    else if (c >= 'A' && c <= 'Z') {
                        resultado.append((char) (((c - 'A') + desplazamiento) % rango + 'A'));
                    }
                    // Caracteres especiales (espacios, números) se quedan igual
                    else {
                        resultado.append(c);
                    }
                }
                break;

            case "descifrar":
                for (int i = 0; i < cadena.length(); i++) {
                    c = cadena.charAt(i);
                    if (c >= 'a' && c <= 'z') {
                        // Se suma 'rango' antes del módulo para evitar resultados negativos
                        resultado.append((char) (((c - 'a') - desplazamiento + rango) % rango + 'a'));
                    } else if (c >= 'A' && c <= 'Z') {
                        resultado.append((char) (((c - 'A') - desplazamiento + rango) % rango + 'A'));
                    } else {
                        resultado.append(c);
                    }
                }
                break;
        }
        return resultado.toString();
    }
}