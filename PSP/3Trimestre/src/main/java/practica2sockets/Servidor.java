package practica2sockets;

import java.io.*;
import java.net.*;

public class Servidor {

    private static int contadorGlobal = 0;

    // Usamos metodos syncronized para modificar la variable global, y asi solo un cliente pueda acceder a ella al mismo tiempo
    public static synchronized void incrementar() {
        contadorGlobal++;
    }

    public static synchronized int getContador() {
        return contadorGlobal;
    }

    public static synchronized void resetear() {
        contadorGlobal = 0;
    }

    public static void main(String[] args) {
        System.out.println("Servidor multihilo arrancado y esperando en puerto 1234...");
        try (ServerSocket server = new ServerSocket(1234)) {
            while (true) {
                Socket cliente = server.accept();
                System.out.println("Nuevo cliente conectado: " + cliente.getInetAddress());
                new Thread(new ManejadorCliente(cliente)).start();
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }

    private static class ManejadorCliente implements Runnable {
        private Socket socket;

        public ManejadorCliente(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
            ) {
                String operacion;
                while ((operacion = in.readLine()) != null) {
                    switch (operacion.toUpperCase()) {
                        case "INC":
                            incrementar();
                            out.println("Contador incrementado");
                            break;
                        case "GET":
                            out.println(getContador());
                            break;
                        case "RESET":
                            resetear();
                            out.println("Contador reseteado");
                            break;
                        case "SALIR":
                            out.println("ADIOS");
                            return;
                        default:
                            out.println("ERROR: Comando no reconocido");
                    }
                }
            } catch (IOException e) {
                System.err.println("Error con un cliente: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Error cerrando socket: " + e.getMessage());
                }
            }
        }
    }
}