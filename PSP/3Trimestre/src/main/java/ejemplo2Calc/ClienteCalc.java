package ejemplo2Calc;

import java.io.*;
import java.net.*;

public class ClienteCalc {
    public static void main(String[] args) {
        String host = "localhost";
        int puerto = 7000;

        try (Socket s = new Socket(host, puerto);
             PrintWriter out = new PrintWriter(s.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()))) {

            // 1. Enviar operación al servidor remoto
            String operacion = "SUMA:5:3";
            System.out.println("Enviando al servidor: " + operacion);
            out.println(operacion);

            // 2. Leer resultado calculado devuelto por el servidor
            String resultado = in.readLine();
            System.out.println("Resultado recibido del servidor: " + resultado);

        } catch (IOException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
    }
}