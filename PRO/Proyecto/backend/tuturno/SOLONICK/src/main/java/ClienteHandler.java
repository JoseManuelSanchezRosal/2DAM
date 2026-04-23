import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteHandler extends Thread {
    private Socket cliente;
    private ChatManager chatManager;
    private String nick;
    private PrintWriter out;

    public ClienteHandler(Socket cliente, ChatManager chatManager){
        this.cliente = cliente;
        this.chatManager = chatManager;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            out = new PrintWriter(cliente.getOutputStream(), true);
            out.println("Introduce tu nick: ");
            this.nick = in.readLine();
            chatManager.anadirCliente(this);

            String mensaje;
            while((mensaje = in.readLine())!=null){
                if(mensaje.startsWith("/privado")){
                    chatManager.enviarPrivado(this, mensaje);
                }else {
                    chatManager.enviarMensaje(this.nick + ": " + mensaje);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String getNick() {
        return nick;
    }

    public void enviarMensaje (String mensaje){
       if(out!=null){
           out.println(mensaje);
       }
    }
}