package ejercicio2;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteNumeros {
    public static void main(String[] args) {
        // Definimos los datos de conexión al servidor
        String direccionServidor = "localhost";
        int puertoServidor = 7002;

        System.out.println("Intentando conectar al servidor...");

        // Usamos try-with-resources para asegurar que todo se cierra al final
        try (
                // 1. Creamos el enchufe (Socket) para conectarnos al servidor
                Socket socketCliente = new Socket(direccionServidor, puertoServidor);

                // 2. Canal para ENVIAR texto al servidor (con auto-flush activado)
                PrintWriter canalSalida = new PrintWriter(socketCliente.getOutputStream(), true);

                // 3. Canal para RECIBIR texto del servidor
                BufferedReader canalEntrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));

                // 4. Lector para capturar lo que el usuario escriba en su teclado
                Scanner lectorTeclado = new Scanner(System.in)
        ) {
            System.out.println("¡Conectado al servidor con éxito!");
            System.out.println("Comandos disponibles: AGREGAR <num>, MOSTRAR, MEDIA, MAXIMO, BORRAR, CERRAR");

            boolean sesionActiva = true;

            // Bucle principal: nos mantenemos preguntando al usuario hasta que decida salir
            while (sesionActiva) {
                // Pedimos al usuario que escriba un comando
                System.out.print("\nEscribe un comando: ");
                String comandoUsuario = lectorTeclado.nextLine();

                // Enviamos el comando tal cual al servidor
                canalSalida.println(comandoUsuario);

                // Si el comando es CERRAR, cambiamos la variable para romper el bucle y terminar
                if (comandoUsuario.equalsIgnoreCase("CERRAR")) {
                    System.out.println("Cerrando sesión...");
                    sesionActiva = false;
                } else {
                    // Si no estamos cerrando, esperamos la respuesta del servidor y la mostramos
                    String respuestaServidor = canalEntrada.readLine();
                    System.out.println("Servidor responde: " + respuestaServidor);
                }
            }

        } catch (IOException excepcionConexion) {
            System.err.println("Error al comunicar con el servidor: " + excepcionConexion.getMessage());
        }
    }
}