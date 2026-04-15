package ActividadEvaluableSockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HiloConexiones extends Thread {
    private int puerto;

    public HiloConexiones(int puerto) {
        this.puerto = puerto;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Servidor de red escuchando en el puerto " + puerto + "...");
            while (!Servidor.juegoIniciado && Servidor.clientes.size() < 10) {
                Socket cliente = serverSocket.accept();
                if (!Servidor.juegoIniciado) {
                    ClienteHandler handler = new ClienteHandler(cliente);
                    Servidor.clientes.add(handler);
                    handler.start();
                } else {
                    cliente.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}