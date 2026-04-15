package ActividadEvaluableSockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClienteHandler extends Thread {
    private Socket cliente;
    private PrintWriter out;
    private BufferedReader in;
    private String nick;
    private int puntuacion;
    private String respuestaActual;
    private int racha;

    private List<Pregunta> misPreguntas;
    private String letraCorrectaActual;

    public ClienteHandler(Socket socket) {
        this.cliente = socket;
        this.puntuacion = 0;
        this.racha = 0;
        this.respuestaActual = "";
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            out = new PrintWriter(cliente.getOutputStream(), true);

            out.println("Por favor, introduce tu nick:");
            nick = in.readLine();
            System.out.println("Jugador conectado: " + nick);
            out.println("Bienvenido al Lobby. Esperando que el administrador inicie la partida...");

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (!Servidor.juegoIniciado) {
                    Servidor.broadcastChat(inputLine, this);
                } else {
                    String respuestaLimpia = inputLine.trim().toLowerCase();

                    if (respuestaLimpia.equals("a") || respuestaLimpia.equals("b") ||
                            respuestaLimpia.equals("c") || respuestaLimpia.equals("d")) {
                        respuestaActual = respuestaLimpia;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(nick + " se ha desconectado.");
        }
    }

    public void prepararPreguntas(List<Pregunta> todasLasPreguntas) {
        this.misPreguntas = new ArrayList<>();
        for (int i = 0; i < todasLasPreguntas.size(); i++) {
            this.misPreguntas.add(todasLasPreguntas.get(i));
        }
        Collections.shuffle(this.misPreguntas);
    }

    public void enviarSiguientePregunta(int ronda) {
        this.respuestaActual = "";

        Pregunta p = misPreguntas.get(ronda);

        List<String> opcionesMezcladas = new ArrayList<>();
        for (int i = 0; i < p.getOpciones().size(); i++) {
            opcionesMezcladas.add(p.getOpciones().get(i));
        }
        Collections.shuffle(opcionesMezcladas);

        StringBuilder mensaje = new StringBuilder("\n" + p.getEnunciado() + "\n");
        String[] letras = {"a", "b", "c", "d"};

        for (int i = 0; i < 4; i++) {
            String op = opcionesMezcladas.get(i);
            mensaje.append(letras[i]).append(") ").append(op).append("\n");

            if (op.equals(p.getCorrecta())) {
                letraCorrectaActual = letras[i];
            }
        }

        enviarMensaje(mensaje.toString());
    }

    public void verificarRespuesta() {
        if (respuestaActual.equals(letraCorrectaActual)) {
            racha++;
            if (racha >= 3) {
                puntuacion += 2;
            } else {
                puntuacion += 1;
            }
            enviarMensaje("-> Resultado: ¡Correcto!");
        } else {
            racha = 0;
            if (!respuestaActual.isEmpty()) {
                puntuacion -= 1;
                enviarMensaje("-> Resultado: Incorrecto. La correcta era la: " + letraCorrectaActual);
            } else {
                enviarMensaje("-> Resultado: Tiempo agotado. La correcta era la: " + letraCorrectaActual);
            }
        }
    }

    public void enviarMensaje(String mensaje) {
        if (out != null) {
            out.println(mensaje);
        }
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public String getNick() {
        return nick;
    }
}