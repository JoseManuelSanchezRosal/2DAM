import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Servidor a la espera de conexiones...");
            Socket clienteSocket = serverSocket.accept();

            System.out.println("¡Cliente conectado desde: " + clienteSocket.getInetAddress() + "!");

        } catch (IOException e) {
            System.err.println("No se pudo abrir el puerto o hubo un error en la conexión.");
        }
    }
}
