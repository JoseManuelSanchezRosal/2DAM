package TriviaSINia;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {

    public static ArrayList<ClienteHandler> clientes = new ArrayList<>();

    // Variables compartidas sin volatile
    private static boolean partidaIniciada = false;
    public static ServerSocket serverSocket; // Accesible para cerrarlo desde HiloComandoStart

    // Métodos sincronizados para sustituir al volatile
    public static synchronized boolean isPartidaIniciada() {
        return partidaIniciada;
    }

    public static synchronized void setPartidaIniciada(boolean estado) {
        partidaIniciada = estado;
    }

    public static void main(String[] args) {
        System.out.println("Servidor arrancado y esperando jugadores...");

        // Arrancamos el hilo que escucha el comando START
        new HiloComandoStart().start();

        try {
            serverSocket = new ServerSocket(5001);

            // Bucle normal y bloqueante. Máximo 10 jugadores.
            while (clientes.size() < 2 && !isPartidaIniciada()) {
                Socket cliente = serverSocket.accept();

                if (!isPartidaIniciada()) {
                    ClienteHandler cl = new ClienteHandler(cliente);
                    cl.start();
                    clientes.add(cl);
                    System.out.println("Nuevo jugador conectado. Total: " + clientes.size());
                } else {
                    // Si se conecta justo cuando ya ha empezado, le rechazamos
                    cliente.close();
                }
            }
        } catch (Exception e) {
            // Cuando HiloComandoStart cierra el ServerSocket, saltará aquí.
            // Si la partida no estaba iniciada, entonces sí fue un error real.
            if (!isPartidaIniciada()) {
                System.err.println("Error de conexión");
            }
        }
    }
}