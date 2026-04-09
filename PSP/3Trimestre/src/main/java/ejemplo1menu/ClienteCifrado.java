package ejemplo1menu;

import java.io.*;
import java.net.*;

public class ClienteCifrado {
    public static void main(String[] args) {
        System.out.println("Cliente conectado");

        try (
                Socket socket = new Socket("localhost", 1234);
                PrintWriter out = new PrintWriter(
                        socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                BufferedReader teclado = new BufferedReader(
                        new InputStreamReader(System.in));
        ) {
            String opcion;
            while (true) {
                System.out.println("\n------------ MENÚ ------------");
                System.out.println("1. Cifrar");
                System.out.println("2. Descifrar");
                System.out.println("3. Salir");
                System.out.println("Elige una opción (1, 2 o 3): ");
                System.out.println("------------------------------");

                opcion = teclado.readLine();
                out.println(opcion);

                if (opcion.equals("3")) {
                    System.out.println("SERVIDOR: " + in.readLine());
                    break;
                }

                String respuesta = in.readLine();

                if (respuesta.equals("Pasame la cadena")) {
                    System.out.println("SERVIDOR: " + respuesta);
                    out.println(teclado.readLine());
                    System.out.println("SERVIDOR: resultado: " + in.readLine());
                } else {
                    System.out.println("SERVIDOR: " + respuesta);
                }
            }

        } catch (IOException e) {
            System.err.println("Error en conexión");
        } catch (ArithmeticException a) {
            System.err.println("División sobre 0" + a.getMessage());
        }
    }
}