package ejemplo2Calc;

import java.io.*;
import java.net.*;

public class ServidorCalc {
    public static void main(String[] args) {
        int puerto = 7000;

        try (ServerSocket ss = new ServerSocket(puerto)) {
            System.out.println("Servidor iniciado. Esperando cliente...");

            // Acepta la conexión del cliente
            try (Socket c = ss.accept();
                 PrintWriter out = new PrintWriter(c.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()))) {

                System.out.println("Cliente conectado.");

                // Leer la petición (ej: "SUMA:5:3")
                String peticion = in.readLine();
                if (peticion != null) {
                    System.out.println("Petición recibida: " + peticion);

                    String[] partes = peticion.split(":");
                    String operacion = partes[0];
                    int num1 = Integer.parseInt(partes[1]);
                    int num2 = Integer.parseInt(partes[2]);

                    int resultado = switch (operacion) {
                        case "SUMA" -> num1 + num2;
                        case "RESTA" -> num1 - num2;
                        default -> -1;
                    };

                    // Enviar respuesta al cliente                   out.println(resultado);
                    System.out.println("Resultado enviado: " + resultado);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}