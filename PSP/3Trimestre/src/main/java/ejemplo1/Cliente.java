package ejemplo1;

import java.io.*;
import java.net.*;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args)throws Exception {
        // 1 Abrir puerto 1234 y esperar conexion
        Socket socket = new Socket("localhost", 1234);
        System.out.println("Conectando al servidor.");

        // 2 Enviar mensaje
        PrintWriter out = new PrintWriter(
                socket.getOutputStream(), true);
        out.println(" Hola desde el Cliente");
        socket.close();
    }
}