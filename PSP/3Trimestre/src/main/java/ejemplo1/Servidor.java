package ejemplo1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) throws Exception {
        // 1 Abrir puerto 1234 y esperar conexion
        ServerSocket server = new ServerSocket(1234);
        System.out.println("Esperando.......");

        Socket cliente = server.accept();
        System.out.println("Cliente Conectado!!");

        // 2 Leer mensaje del cliente
        BufferedReader in = new BufferedReader(
                new InputStreamReader(cliente.getInputStream()));
        System.out.println("Recibido:" + in.readLine());

        cliente.close();
        server.close();
    }
}
