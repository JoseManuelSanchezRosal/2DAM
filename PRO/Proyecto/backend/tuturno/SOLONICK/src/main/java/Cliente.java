import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try(
                Socket socket = new Socket("Localhost", 5111);// SOCKET
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));// LECTURA
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);// ESCRITURA
                Scanner sc = new Scanner(System.in);// TECLADO
                ) {

            System.out.println("Conectado al servidor");// CONECTADO AL SERVIDOR
            new ReceptorMensajes(in).start();// HILO CLIENTEHANDLER ESCUCHA LANZADO

            while(true){
                String mensaje = sc.nextLine();
                out.println(mensaje);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}