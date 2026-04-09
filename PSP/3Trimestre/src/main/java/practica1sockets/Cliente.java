package practica1sockets;

import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        System.out.println("Cliente conectado");

        try (
                // FASE 0: CONEXION COMUNICACION SOCKET
                Socket socket = new Socket("localhost", 1234);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
        ) {

            // FASE 1: BUCLE DE AUTENTICACION
            boolean autenticado = false;
            String respuestaServidor;

            // El cliente reacciona a los comandos de control del servidor PARA EL LOGIN
            while ((respuestaServidor = in.readLine()) != null) {
                if (respuestaServidor.equals("REQ_USER")) {
                    System.out.print("Introduce Usuario: ");
                    out.println(teclado.readLine());
                } else if (respuestaServidor.equals("REQ_PASS")) {
                    System.out.print("Introduce Contraseña: ");
                    out.println(teclado.readLine());
                } else if (respuestaServidor.equals("AUTH_OK")) {
                    System.out.println("SERVIDOR: Autenticacion exitosa.\n");
                    autenticado = true;
                    break;
                } else if (respuestaServidor.equals("AUTH_FAIL")) {
                    System.out.println("SERVIDOR: Credenciales incorrectas. Intentalo de nuevo.");
                } else if (respuestaServidor.equals("EXIT_AUTH")) {
                    System.out.println("SERVIDOR: Limite de intentos alcanzado. Desconectando....");
                    return; // Termina la ejecucion del cliente
                }
            }
            if (!autenticado) return;

            // FASE 2: BUCLE DEL MENU
            String opcion;
            while (true) {
                System.out.println("\n------------ MENU ------------");
                System.out.println("1. Sumar");
                System.out.println("2. Contador");
                System.out.println("3. Invierte");
                System.out.println("4. EsPrimo");
                System.out.println("5. Salir");
                System.out.println("------------------------------");
                System.out.print("Elige una opcion: ");

                opcion = teclado.readLine();
                out.println(opcion);

                if (opcion.equals("5")) {
                    // El servidor nos manda ADIOS y nosotros lo mostramos en CONSOLA
                    System.out.println("SERVIDOR: " + in.readLine());
                    break;
                }
                String respuesta = in.readLine();

                // "Hack" estructural: Si el servidor empieza la frase con "Dame",
                // el cliente asume que necesita enviar parámetros adicionales.
                if (respuesta != null && respuesta.startsWith("Dame")) {
                    System.out.println("SERVIDOR: " + respuesta);
                    out.println(teclado.readLine());
                    System.out.println("SERVIDOR: resultado: " + in.readLine());
                } else {
                    System.out.println("SERVIDOR: " + respuesta);
                }
            }
        } catch (IOException e) {
            System.err.println("Error en conexion: " + e.getMessage());
        }
    }
}