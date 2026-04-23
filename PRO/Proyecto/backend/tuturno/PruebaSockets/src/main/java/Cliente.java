import java.io.IOException;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("Localhost", 5005);

            // Se intenta establecer la conexión física con el servidor
            // Si el servidor no está encendido en ese puerto, saltará al catch inmediatamente
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Conexión establecida con el servidor.");


        } catch (IOException e) {
            System.err.println("Error: Asegúrate de que el servidor esté corriendo en el puerto 5000.");
        }
    }
}
