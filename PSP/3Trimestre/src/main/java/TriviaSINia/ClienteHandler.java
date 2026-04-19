package TriviaSINia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteHandler extends Thread {

    private Socket cliente;
    private boolean haRespondido;
    private String respuestaActual;
    private int puntos;
    private String nick;

    private BufferedReader in;
    private PrintWriter out;

    public ClienteHandler(Socket socket) throws Exception {
        this.cliente = socket;
        this.puntos = 0;
        this.respuestaActual = null;
        this.haRespondido = false;

        this.in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
        this.out = new PrintWriter(cliente.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            // La primera lectura que hace el servidor del cliente es su nick, al introducirlo lo sacamos por consola del servidor
            this.nick = in.readLine();
            if(this.nick != null){
                System.out.println("El usuario " + this.nick + " se ha conectado");
            }

            String mensaje;
            while ((mensaje = in.readLine()) != null) {
                // Limpiamos espacios accidentales
                String respuesta = mensaje.trim();

                // Comprobamos que la entrada sea a, b c o d
                if (respuesta.equalsIgnoreCase("A") || respuesta.equalsIgnoreCase("B") || respuesta.equalsIgnoreCase("C") || respuesta.equalsIgnoreCase("D")) {
                    registrarRespuestaSincronizada(respuesta);
                } else {
                    // Opcional: Avisar al cliente si escribe otra cosa
                    enviarMensaje("Formato incorrecto. Solo se admite A, B, C o D.");
                }
            }
        } catch (IOException e) {
            System.err.println("Cliente desconectado: " + nick);
        }
    }

    // Método sincronizado para sustituir a volatile
    private synchronized void registrarRespuestaSincronizada(String letra) {
        if (GameManager.isRondaAbierta() && !haRespondido) {
            this.respuestaActual = letra;
            this.haRespondido = true;
            enviarMensaje("Respuesta registrada");
        }
    }

    public String mostrarNota() {
        String nota = nick + " tiene: " + puntos + " puntos";
        return nota; // Ya no hace el println aquí, se usa en GameManager
    }

    public void enviarMensaje(String msg) {
        out.println(msg);
    }

    // Sincronizado para lectura/escritura segura
    public synchronized void limpiaRespuesta() {
        this.haRespondido = false;
        this.respuestaActual = null;
    }

    // Sincronizado para evaluación segura
    public synchronized void corregirRespuesta(String sol) {
        if (respuestaActual != null) {
            if (respuestaActual.equalsIgnoreCase(sol)) {
                puntos++;
                enviarMensaje("Respuesta correcta (+1 pto)");
            } else {
                enviarMensaje("Error. La respuesta correcta era: " + sol + " (0 ptos)");
            }
        } else {
            enviarMensaje("Respuesta en BLACO. (0 ptos)");
        }
    }

    public int getPuntos() {
        return puntos;
    }

    public String getNick() {
        return nick;
    }
}