package practica2sockets;

import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        System.out.println("Iniciando cliente");

        try (
                Socket socket = new Socket("localhost", 1234);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in))
        ) {
            String comando;
            while (true) {
                System.out.println("\n--- MENU DE COMANDOS ---");
                System.out.println("INC: Incrementar contador");
                System.out.println("GET: Consultar contador");
                System.out.println("RESET: Poner contador a cero");
                System.out.println("SALIR: Desconectar");

                comando = teclado.readLine();

                if (comando == null || comando.trim().isEmpty()) {
                    continue;
                }

                out.println(comando);

                if (comando.equalsIgnoreCase("SALIR")) {
                    System.out.println("SERVIDOR: " + in.readLine());
                    break;
                }

                String respuesta = in.readLine();
                System.out.println("SERVIDOR: " + respuesta);
            }
        } catch (IOException e) {
            System.err.println("Error en conexion: " + e.getMessage());
        }
    }
}