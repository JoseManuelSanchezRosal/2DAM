
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5111);
            System.out.println("Servidor conectado");

            ChatManager chatManager = new ChatManager();
            while (true){
                Socket cliente = serverSocket.accept();
                new ClienteHandler(cliente, chatManager).start();

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}