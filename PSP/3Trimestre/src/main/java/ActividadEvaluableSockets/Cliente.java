package ActividadEvaluableSockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("localhost", 7000);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in))
        ) {
            HiloLecturaCliente hiloLectura = new HiloLecturaCliente(in);
            hiloLectura.start();

            String inputUsuario;
            while ((inputUsuario = teclado.readLine()) != null) {
                out.println(inputUsuario);
            }

        } catch (IOException e) {
            System.out.println("Error al conectar con el servidor.");
        }
    }
}