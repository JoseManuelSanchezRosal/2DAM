import java.util.ArrayList;

public class ChatManager {
    private ArrayList<ClienteHandler> clientes = new ArrayList<>();

    public synchronized void anadirCliente(ClienteHandler clienteNuevo){
        clientes.add(clienteNuevo);
        enviarMensaje(clienteNuevo.getNick() + " se ha conectado");
    }
    public synchronized void enviarMensaje(String mensaje) {
        for (ClienteHandler cl : clientes){
            cl.enviarMensaje(mensaje);
        }
    }
    public synchronized void enviarPrivado(ClienteHandler emisor, String mensaje){
        String[] partes = mensaje.split(" ", 3);
        if(partes.length < 3){
            emisor.enviarMensaje("Sintaxis incorrecta (/privado + nick + mensaje");
        }
        String nickBuscado = partes[1];
        String mensajePrivado = partes[2];
        Boolean encontrado = false;
        for(ClienteHandler cl : clientes){
            if(cl.getNick().equalsIgnoreCase(nickBuscado)){
                encontrado = true;
                cl.enviarMensaje("Privado de " + emisor.getNick() + " :" + mensajePrivado);
                emisor.enviarMensaje("Mensaje enviado");
                break;
            }
        }
        if (!encontrado) {
            emisor.enviarMensaje("Destinatario " + nickBuscado + " no encontrado");
        }
    }
}