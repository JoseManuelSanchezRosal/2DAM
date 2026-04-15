package Ejercicio1menuCifrado;

import java.io.*;
import java.net.*;

public class ClienteCifrado {
    public static void main(String[] args) {
        System.out.println("Cliente conectado");

        // Usamos try-with-resources para asegurar el cierre automático de sockets y flujos
        try (
                // Conexión al servidor en localhost y puerto 1234
                Socket socket = new Socket("localhost", 1234);
                // Flujo de salida para enviar datos al servidor
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                // Flujo de entrada para recibir datos del servidor
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                // Flujo para leer la entrada del usuario por consola
                BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
        ) {
            String opcion;
            while (true) {
                // Interfaz de usuario por consola
                System.out.println("\n------------ MENÚ ------------");
                System.out.println("- escribir 'cifrar'");
                System.out.println("- escribir 'descifrar'");
                System.out.println("- escribir 'salir'");
                System.out.print("Elige una opción: ");
                System.out.println("\n------------------------------");

                // Leemos la opción, eliminamos espacios y convertimos a minúsculas
                opcion = teclado.readLine().trim().toLowerCase();

                // Enviamos la opción elegida al servidor
                out.println(opcion);

                // Si el usuario decide salir, leemos la despedida del servidor y rompemos el bucle
                if (opcion.equals("salir")) {
                    System.out.println("SERVIDOR: " + in.readLine());
                    break;
                }

                // Leemos la respuesta del servidor tras enviar la opción
                String respuesta = in.readLine();

                // Protocolo: Si el servidor responde "Pasame la cadena", procedemos a enviar el texto
                if (respuesta != null && respuesta.equals("Pasame la cadena")) {
                    System.out.println("SERVIDOR: " + respuesta);

                    // Leemos el texto que el usuario quiere procesar
                    String textoUsuario = teclado.readLine();
                    out.println(textoUsuario);

                    // Mostramos el resultado final enviado por el servidor
                    System.out.println("SERVIDOR: resultado: " + in.readLine());
                } else {
                    // Si el servidor envía un error o mensaje inesperado, lo mostramos
                    System.out.println("SERVIDOR: " + (respuesta != null ? respuesta : "Sin respuesta"));
                }
            }

        } catch (IOException e) {
            System.err.println("Error en conexión: " + e.getMessage());
        }
    }
}